package kr.joberchip.server.v1.page.service;

import java.util.*;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.block.repository.*;
import kr.joberchip.server.v1.page.controller.dto.*;
import kr.joberchip.server.v1.page.controller.dto.MoveBlockDTO;
import kr.joberchip.server.v1.page.repository.SharePagePrivilegeRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import kr.joberchip.server.v1.space.repository.SpaceRepository;
import kr.joberchip.server.v1.storage.service.S3StorageService;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePageService {
  private final MapBlockRepository mapBlockRepository;
  private final VideoBlockRepository videoBlockRepository;
  private final ImageBlockRepository imageBlockRepository;
  private final TemplateBlockRepository templateBlockRepository;
  private final LinkBlockRepository linkBlockRepository;
  private final TextBlockRepository textBlockRepository;
  private final UserRepository userRepository;
  private static final String defaultPageTitle = "의 스페이스";
  private static final String defaultPageDescription = "의 스페이스입니다.";

  private final SpaceRepository spaceRepository;
  private final SharePageRepository sharePageRepository;
  private final SharePagePrivilegeRepository sharePagePrivilegeRepository;
  private final S3StorageService s3StorageService;

  /**
   * 읽기 또는 수정 권한이 있는 사용자가 해당 페이지를 조회하는 경우 비공개 블럭을 포함한 상세 페이지 내용
   *
   * @param userId 사용자 ID
   * @param pageId 조회 대상 페이지 ID
   * @return 비공개 블럭을 포함한 상세 페이지 내용
   */
  @Transactional(readOnly = true)
  public SharePageDetailResponseDTO getDetails(Long userId, UUID pageId) {

    // 페이지 권한 정보가 없는 사용자 - 로그인 하지 않은 상태와 동일
    SharePagePrivilege sharePagePrivilege =
        sharePagePrivilegeRepository.findByUserIdAndSharePageId(userId, pageId).orElse(null);

    if (sharePagePrivilege == null) return getDetails(pageId);

    // 페이지 권한이 있는 사용자 - 모든 비공개 블럭도 볼 수 있음.

    SharePage currentSharePage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    log.info("[SharePageService] Current SharePage : {}", currentSharePage);

    return SharePageDetailResponseDTO.fromEntityWithPrivilege(
        currentSharePage, sharePagePrivilege.getPrivilegeType());
  }

  /**
   * 로그인 하지 않은 상태에서 페이지를 조회하는 경우
   *
   * @param pageId 페이지 ID
   * @return 해당 페이지가 공개 상태인 경우 공개 설정된 블럭만을 포함한 상세 페이지
   */
  @Transactional(readOnly = true)
  public SharePageDetailResponseDTO getDetails(UUID pageId) {

    SharePage currentSharePage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    log.info("[SharePageService] Current SharePage : {}", currentSharePage);

    // 비공개 상태 접근 금지
    if (!currentSharePage.getVisible())
      throw new ApiClientException(ErrorMessage.NOT_VISIBLE_SHARE_PAGE);

    // 권한 정보 없이 페이지 내용 전달
    return SharePageDetailResponseDTO.fromEntity(currentSharePage);
  }

  @Transactional
  public UUID create(SharePageRequestDTO createPageRequest) {

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
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.USER_ENTITY_NOT_FOUND));

    SharePage defaultPage =
        SharePage.of(
            user.getUsername() + defaultPageTitle, user.getUsername() + defaultPageDescription);

    sharePageRepository.save(defaultPage);

    return defaultPage.getObjectId();
  }

  @Transactional(readOnly = true)
  public SharePageTreeResponseDTO.PageTreeNode getPageBreadCrumbBar(UUID pageId) {
    SharePage current =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

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

  @Transactional
  public void moveBlocks(MoveBlockDTO moveBlockDTO) {
    moveBlockDTO
        .blocks()
        .forEach(
            blockDTO -> {
              switch (blockDTO.blockType()) {
                case PAGE -> sharePageRepository
                    .findSharePageByObjectId(blockDTO.blockId())
                    .ifPresent(
                        sharePage -> {
                          sharePage.setX(blockDTO.x());
                          sharePage.setY(blockDTO.y());
                          sharePage.setW(blockDTO.w());
                          sharePage.setH(blockDTO.h());
                          sharePageRepository.save(sharePage);
                        });

                case TEXT -> textBlockRepository
                    .findById(blockDTO.blockId())
                    .ifPresent(
                        textBlock -> {
                          textBlock.setX(blockDTO.x());
                          textBlock.setY(blockDTO.y());
                          textBlock.setW(blockDTO.w());
                          textBlock.setH(blockDTO.h());
                          textBlockRepository.save(textBlock);
                        });

                case LINK -> linkBlockRepository
                    .findById(blockDTO.blockId())
                    .ifPresent(
                        linkBlock -> {
                          linkBlock.setX(blockDTO.x());
                          linkBlock.setY(blockDTO.y());
                          linkBlock.setW(blockDTO.w());
                          linkBlock.setH(blockDTO.h());
                          linkBlockRepository.save(linkBlock);
                        });

                case TEMPLATE -> templateBlockRepository
                    .findById(blockDTO.blockId())
                    .ifPresent(
                        templateBlock -> {
                          templateBlock.setX(blockDTO.x());
                          templateBlock.setY(blockDTO.y());
                          templateBlock.setW(blockDTO.w());
                          templateBlock.setH(blockDTO.h());
                          templateBlockRepository.save(templateBlock);
                        });

                case MAP -> mapBlockRepository
                    .findById(blockDTO.blockId())
                    .ifPresent(
                        mapBlock -> {
                          mapBlock.setX(blockDTO.x());
                          mapBlock.setY(blockDTO.y());
                          mapBlock.setW(blockDTO.w());
                          mapBlock.setH(blockDTO.h());
                          mapBlockRepository.save(mapBlock);
                        });

                case IMAGE -> imageBlockRepository
                    .findById(blockDTO.blockId())
                    .ifPresent(
                        imageBlock -> {
                          imageBlock.setX(blockDTO.x());
                          imageBlock.setY(blockDTO.y());
                          imageBlock.setW(blockDTO.w());
                          imageBlock.setH(blockDTO.h());
                          imageBlockRepository.save(imageBlock);
                        });

                case VIDEO -> videoBlockRepository
                    .findById(blockDTO.blockId())
                    .ifPresent(
                        videoBlock -> {
                          videoBlock.setX(blockDTO.x());
                          videoBlock.setY(blockDTO.y());
                          videoBlock.setW(blockDTO.w());
                          videoBlock.setH(blockDTO.h());
                          videoBlockRepository.save(videoBlock);
                        });
              }
            });
  }

  public SharePageDetailResponseDTO modify(UUID pageId, SharePageModifyDTO sharePageModifyDTO) {
    SharePage currentPage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    if (sharePageModifyDTO.title() != null) currentPage.setTitle(sharePageModifyDTO.title());

    if (sharePageModifyDTO.description() != null)
      currentPage.setDescription(sharePageModifyDTO.description());

    if (sharePageModifyDTO.profileImage() != null) {
      s3StorageService.delete(currentPage.getProfileImageLink());
      currentPage.setProfileImageLink(s3StorageService.store(sharePageModifyDTO.profileImage()));
    }

    if (sharePageModifyDTO.visible() != null) {
      currentPage.setVisible(sharePageModifyDTO.visible());
    }

    if (sharePageModifyDTO.parentPageId() != null
        && sharePageModifyDTO.parentPageId() != currentPage.getParentObjectId()) {

      SharePage beforeParent =
          sharePageRepository
              .findById(currentPage.getParentObjectId())
              .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));
      beforeParent.getChildPages().remove(currentPage);

      SharePage afterParent =
          sharePageRepository
              .findById(sharePageModifyDTO.parentPageId())
              .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));
      afterParent.addChildPage(currentPage);
    }

    sharePageRepository.save(currentPage);

    return SharePageDetailResponseDTO.fromEntity(currentPage);
  }

  @Transactional
  public void delete(UUID pageId) {
    Optional<SharePage> optionalSharePage = sharePageRepository.findSharePageByObjectId(pageId);
    if (optionalSharePage.isPresent()) {
      Set<SharePage> children = optionalSharePage.get().getChildPages();
      children.forEach(child -> delete(child.getObjectId()));
      if (optionalSharePage.get().getProfileImageLink() != null
          && optionalSharePage
              .get()
              .getProfileImageLink()
              .equals("https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png")) {
        sharePageRepository.deleteById(pageId);
      } else if (optionalSharePage.get().getProfileImageLink() == null) {
        sharePageRepository.deleteById(pageId);
      } else {
        s3StorageService.delete(optionalSharePage.get().getProfileImageLink());
        sharePageRepository.deleteById(pageId);
      }
    }

    log.info("Delete Successful for pageId - {}", pageId);
  }
}
