package kr.joberchip.server.v1.share.page.service;

import java.util.*;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.core.user.PagePermission;
import kr.joberchip.core.user.types.PermissionType;
import kr.joberchip.server.v1.share.page.dto.SharePageSummaryResponse;
import kr.joberchip.server.v1.share.page.dto.request.CreatePageRequest;
import kr.joberchip.server.v1.share.page.dto.request.ModifyPagePermission;
import kr.joberchip.server.v1.share.page.dto.response.PageTreeResponse;
import kr.joberchip.server.v1.share.page.dto.response.SharePageDetailResponse;
import kr.joberchip.server.v1.share.page.repository.SharePageRepository;
import kr.joberchip.server.v1.space.repository.SpaceRepository;
import kr.joberchip.server.v1.user.repository.PagePermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePageService {

  private final SpaceRepository spaceRepository;
  private final SharePageRepository sharePageRepository;
  private final PagePermissionRepository pagePermissionRepository;

  @Transactional(readOnly = true)
  public SharePageDetailResponse getDetails(UUID pageId) {

    SharePage sharePage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    return SharePageDetailResponse.fromEntity(sharePage);
  }

  @Transactional
  public SharePageSummaryResponse create(Long userId, CreatePageRequest createPageRequest) {
    SharePage generated = createPageRequest.toEntity();

    sharePageRepository.save(generated);

    SharePage parent = sharePageRepository.getReferenceById(createPageRequest.parentPageId());
    parent.addChildPage(generated);

    PagePermission newPermission =
        PagePermission.of(userId, generated.getPageId(), PermissionType.EDIT);

    pagePermissionRepository.save(newPermission);

    log.info("Generated page : {}", generated);

    return SharePageSummaryResponse.fromEntity(generated);
  }

  @Transactional(readOnly = true)
  public PageTreeResponse.PageTreeNode getPageTree(UUID spaceId) {
    SharePage rootPage =
        spaceRepository.findById(spaceId).orElseThrow(EntityNotFoundException::new).getMainPage();

    PageTreeResponse.PageTreeNode rootNode =
        PageTreeResponse.PageTreeNode.of(rootPage.getPageId(), rootPage.getTitle());

    Queue<PageTreeResponse.PageTreeNode> nodeQueue = new LinkedList<>();

    nodeQueue.offer(rootNode);

    while (!nodeQueue.isEmpty()) {
      PageTreeResponse.PageTreeNode currentNode = nodeQueue.poll();
      SharePage currentPage =
          sharePageRepository
              .findById(currentNode.pageId())
              .orElseThrow(EntityNotFoundException::new);

      if (currentPage.getChildPages() == null) continue;

      currentPage
          .getChildPages()
          .forEach(
              child -> {
                PageTreeResponse.PageTreeNode childNode =
                    PageTreeResponse.PageTreeNode.of(child.getPageId(), child.getTitle());
                currentNode.addChildNote(childNode);
                nodeQueue.offer(childNode);
              });
    }

    return rootNode;
  }

  public void createDefaultPage(Long userId) {}

  @Transactional
  public void modifyPagePermission(Long userId, UUID pageId, PermissionType permissionType) {
    PagePermission pagePermission =
        pagePermissionRepository
            .findByUserIdAndPageId(userId, pageId)
            .orElseThrow(EntityNotFoundException::new);

    pagePermission.modifyPermission(permissionType);
  }

  @Transactional
  public void modifyPagePermission(ModifyPagePermission modifyPagePermission) {
    PagePermission pagePermission =
        pagePermissionRepository
            .findByUserIdAndPageId(modifyPagePermission.userId(), modifyPagePermission.pageId())
            .orElseThrow(EntityNotFoundException::new);

    pagePermission.modifyPermission(modifyPagePermission.permissionType());
  }
}
