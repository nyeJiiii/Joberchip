package kr.joberchip.server.v1.page.service;

import java.util.*;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._errors.exceptions.filter.Exception403;
import kr.joberchip.server.v1.page.controller.dto.SharePageDetailResponseDTO;
import kr.joberchip.server.v1.page.controller.dto.SharePagePrivilegeDTO;
import kr.joberchip.server.v1.page.controller.dto.SharePageRequestDTO;
import kr.joberchip.server.v1.page.controller.dto.SharePageTreeResponseDTO;
import kr.joberchip.server.v1.page.repository.SharePagePrivilegeRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import kr.joberchip.server.v1.space.repository.SpaceRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePageService {
  private final UserRepository userRepository;
  private static final String defaultPageTitle = "의 스페이스";
  private static final String defaultPageDescription = "의 스페이스입니다.";

  private final SpaceRepository spaceRepository;
  private final SharePageRepository sharePageRepository;
  private final SharePagePrivilegeRepository sharePagePrivilegeRepository;

  @Transactional(readOnly = true)
  public SharePageDetailResponseDTO getDetails(Long userId, UUID pageId) {

    SharePage sharePage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    // 로그인 하지 않은 경우
    if (userId == null) {

      // 비공개 상태 접근 금지
      if (!sharePage.getVisible()) throw new Exception403(HttpStatus.FORBIDDEN.getReasonPhrase());

      // 권한 정보 없이 페이지 내용 전달
      return SharePageDetailResponseDTO.fromEntity(sharePage);
    }

    // 로그인 상태

    // 권한이 없는 경우
    if (!sharePage.getVisible()
        && !sharePagePrivilegeRepository.existsByUserIdAndSharePageId(userId, pageId))
      throw new Exception403(HttpStatus.FORBIDDEN.getReasonPhrase());

    return sharePagePrivilegeRepository
        .findByUserIdAndSharePageId(userId, pageId)
        .map(
            sharePagePrivilege ->
                SharePageDetailResponseDTO.fromEntityWithPrivilege(
                    sharePage, sharePagePrivilege.getPrivilegeType()))
        .orElseGet(() -> SharePageDetailResponseDTO.fromEntity(sharePage));
  }

  @Transactional
  public UUID create(Long userId, SharePageRequestDTO createPageRequest) {

    SharePage generatedSharePage = createPageRequest.toEntity();

    sharePageRepository.save(generatedSharePage);

    SharePage parent =
        sharePageRepository
            .findById(createPageRequest.parentPageId())
            .orElseThrow(EntityNotFoundException::new);

    parent.addChildPage(generatedSharePage);

    log.info("Generated page : {}", generatedSharePage);

    return generatedSharePage.getObjectId();
  }

  @Transactional(readOnly = true)
  public SharePageTreeResponseDTO.PageTreeNode getPageTree(UUID spaceId) {
    SharePage rootPage =
        spaceRepository.findById(spaceId).orElseThrow(EntityNotFoundException::new).getMainPage();

    SharePageTreeResponseDTO.PageTreeNode rootNode =
        SharePageTreeResponseDTO.PageTreeNode.of(
            rootPage.getParentObjectId(), rootPage.getObjectId(), rootPage.getTitle());

    Queue<SharePageTreeResponseDTO.PageTreeNode> nodeQueue = new LinkedList<>();

    nodeQueue.offer(rootNode);

    while (!nodeQueue.isEmpty()) {
      SharePageTreeResponseDTO.PageTreeNode currentNode = nodeQueue.poll();
      SharePage currentPage =
          sharePageRepository
              .findById(currentNode.pageId())
              .orElseThrow(EntityNotFoundException::new);

      if (currentPage.getChildPages() == null) continue;

      currentPage
          .getChildPages()
          .forEach(
              child -> {
                SharePageTreeResponseDTO.PageTreeNode childNode =
                    SharePageTreeResponseDTO.PageTreeNode.of(
                        child.getParentObjectId(), child.getObjectId(), child.getTitle());
                currentNode.addChild(childNode);
                nodeQueue.offer(childNode);
              });
    }

    return rootNode;
  }

  public UUID createDefaultPage(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

    SharePage defaultPage =
        SharePage.of(
            user.getUsername() + defaultPageTitle, user.getUsername() + defaultPageDescription);

    sharePageRepository.save(defaultPage);

    SharePagePrivilege sharePagePrivilege =
        SharePagePrivilege.of(userId, defaultPage.getObjectId(), PrivilegeType.EDIT);

    sharePagePrivilegeRepository.save(sharePagePrivilege);

    return defaultPage.getObjectId();
  }

  @Transactional
  public void modifyPrivilege(SharePagePrivilegeDTO sharePagePermissionDTO) {
    SharePagePrivilege pagePermission =
        sharePagePrivilegeRepository
            .findByUserIdAndSharePageId(
                sharePagePermissionDTO.userId(), sharePagePermissionDTO.sharePageId())
            .orElseThrow(EntityNotFoundException::new);

    pagePermission.modifyPrivilege(sharePagePermissionDTO.privilegeType());
  }

  @Transactional(readOnly = true)
  public SharePageTreeResponseDTO.PageTreeNode getPageBreadCrumbBar(UUID pageId) {
    SharePage current =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    SharePageTreeResponseDTO.PageTreeNode currentNode =
        SharePageTreeResponseDTO.PageTreeNode.of(
            current.getParentObjectId(), current.getObjectId(), current.getTitle());

    while (sharePageRepository.existsById(currentNode.parentId())) {
      SharePage parent =
          sharePageRepository
              .findById(currentNode.parentId())
              .orElseThrow(EntityNotFoundException::new);

      SharePageTreeResponseDTO.PageTreeNode parentNode =
          SharePageTreeResponseDTO.PageTreeNode.of(
              parent.getParentObjectId(), parent.getObjectId(), parent.getTitle());
      parentNode.addChild(currentNode);
      currentNode = parentNode;
    }

    return currentNode;
  }
}
