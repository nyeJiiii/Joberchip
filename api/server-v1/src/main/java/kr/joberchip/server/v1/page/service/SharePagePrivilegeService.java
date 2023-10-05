package kr.joberchip.server.v1.page.service;

import java.util.Stack;
import java.util.UUID;
import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.page.controller.dto.SharePagePrivilegeDTO;
import kr.joberchip.server.v1.page.controller.dto.SharePagePrivilegeResponseDTO;
import kr.joberchip.server.v1.page.repository.SharePagePrivilegeRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import kr.joberchip.server.v1.space.controller.dto.SpaceInviteRequestDTO;
import kr.joberchip.server.v1.space.repository.SpaceRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePagePrivilegeService {
  private final SharePageRepository sharePageRepository;
  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;
  private final SharePagePrivilegeRepository sharePagePrivilegeRepository;

  @Transactional
  public void registerPrivilege(Long userId, UUID sharePageId, PrivilegeType privilegeType) {

    SharePagePrivilege sharePagePrivilege =
        SharePagePrivilege.of(userId, sharePageId, privilegeType);

    sharePagePrivilegeRepository.save(sharePagePrivilege);

    log.info(
        "[SharePagePrivilegeService] Privilege registered : {}, {}, {}",
        userId,
        sharePageId,
        privilegeType);
  }

  @Transactional
  public void modifyPrivilege(SharePagePrivilegeDTO sharePagePrivilegeDTO) {
    Long targetUserId = sharePagePrivilegeDTO.userId();
    PrivilegeType targetPrivilege = sharePagePrivilegeDTO.privilegeType();

    if (!userRepository.existsById(targetUserId))
      throw new ApiClientException(ErrorMessage.USER_ENTITY_NOT_FOUND);

    if (!sharePageRepository.existsById(sharePagePrivilegeDTO.sharePageId()))
      throw new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND);

    // 하위 페이지가 존재하면 해당 페이지에도 동일한 권한을 부여
    Stack<UUID> pageIdStack = new Stack<>();
    pageIdStack.push(sharePagePrivilegeDTO.sharePageId());

    while (!pageIdStack.isEmpty()) {
      UUID currentPageId = pageIdStack.pop();

      // 기존에 부여된 권한이 있으면 변경하고, 없으면 새로 부여
      sharePagePrivilegeRepository
          .findByUserIdAndSharePageId(targetUserId, currentPageId)
          .ifPresentOrElse(
              sharePagePrivilege -> sharePagePrivilege.setPrivilegeType(targetPrivilege),
              () -> {
                sharePagePrivilegeRepository.save(sharePagePrivilegeDTO.toEntity());
              });
    }
  }

  @Transactional(readOnly = true)
  public SharePagePrivilegeResponseDTO getPrivilege(Long userId, UUID pageId) {
    if (!sharePageRepository.existsById(pageId))
      throw new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND);

    SharePagePrivilege sharePagePrivilege =
        sharePagePrivilegeRepository.findByUserIdAndSharePageId(userId, pageId).orElse(null);

    return sharePagePrivilege != null
        ? SharePagePrivilegeResponseDTO.fromEntity(sharePagePrivilege)
        : null;
  }

  @Transactional(readOnly = true)
  public void checkPrivilege(Long userId, UUID pageId, PrivilegeType privilegeType) {
    sharePagePrivilegeRepository
        .findByUserIdAndSharePageId(userId, pageId)
        .filter(sharePagePrivilege -> sharePagePrivilege.getPrivilegeType() == privilegeType)
        .orElseThrow(() -> new ApiClientException(ErrorMessage.INVALID_PRIVILEGE));

    log.info(
        "Privilege : userId - {}, pageId - {}, privilege type - {}", userId, pageId, privilegeType);
  }

  @Transactional(readOnly = true)
  public void checkReadPrivilege(Long userId, UUID pageId) {
    sharePagePrivilegeRepository
        .findByUserIdAndSharePageId(userId, pageId)
        .filter(
            sharePagePrivilege ->
                sharePagePrivilege.getPrivilegeType() == PrivilegeType.READ
                    || sharePagePrivilege.getPrivilegeType() == PrivilegeType.EDIT)
        .orElseThrow(() -> new ApiClientException(ErrorMessage.NO_PRIVILEGE));

    log.info(
        "Privilege Checked : userId - {}, pageId - {}, privilege type - {}",
        userId,
        pageId,
        PrivilegeType.READ);
  }

  @Transactional(readOnly = true)
  public void checkEditPrivilege(Long userId, UUID pageId) {
    sharePagePrivilegeRepository
        .findByUserIdAndSharePageId(userId, pageId)
        .filter(sharePagePrivilege -> sharePagePrivilege.getPrivilegeType() == PrivilegeType.EDIT)
        .orElseThrow(() -> new ApiClientException(ErrorMessage.NO_PRIVILEGE));

    log.info(
        "Privilege Checked : userId - {}, pageId - {}, privilege type - {}",
        userId,
        pageId,
        PrivilegeType.EDIT);
  }

  public void registerGivenPrivilegeForAllSpaceSubPage(
      SpaceInviteRequestDTO spaceInviteRequestDTO, PrivilegeType privilegeType) {

    Long targetUserId = spaceInviteRequestDTO.userId();

    if (userRepository.existsById(targetUserId))
      throw new ApiClientException(ErrorMessage.USER_ENTITY_NOT_FOUND);

    if (spaceRepository.existsById(spaceInviteRequestDTO.spaceId()))
      throw new ApiClientException(ErrorMessage.SPACE_ENTITY_NOT_FOUND);

    UUID currentPageId =
        spaceRepository
            .findById(spaceInviteRequestDTO.spaceId())
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SPACE_ENTITY_NOT_FOUND))
            .getMainPage()
            .getObjectId();

    log.info("[SharePagePrivilegeService] Main Page Id : {}", currentPageId);

    Stack<UUID> pageIdStack = new Stack<>();
    pageIdStack.push(currentPageId);

    while (!pageIdStack.isEmpty()) {
      currentPageId = pageIdStack.pop();

      sharePagePrivilegeRepository.save(
          SharePagePrivilege.of(targetUserId, currentPageId, privilegeType));

      sharePageRepository
          .findSharePageByObjectId(currentPageId)
          .ifPresent(
              sharePage ->
                  sharePage
                      .getChildPages()
                      .forEach(childPage -> pageIdStack.push(childPage.getObjectId())));
    }

    log.info(
        "[SharePagePrivilegeService] Register Successful : userId - {}, spaceId - {}, Privilege Type - {}",
        spaceInviteRequestDTO.userId(),
        spaceInviteRequestDTO.spaceId(),
        privilegeType);
  }
}
