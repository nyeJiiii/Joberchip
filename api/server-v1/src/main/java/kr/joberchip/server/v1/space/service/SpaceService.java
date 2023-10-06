package kr.joberchip.server.v1.space.service;

import java.util.*;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.core.space.Space;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.page.controller.dto.SharePageTreeResponseDTO;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import kr.joberchip.server.v1.space.controller.dto.ParticipationInfoResponseDTO;
import kr.joberchip.server.v1.space.repository.SpaceParticipationInfoRepository;
import kr.joberchip.server.v1.space.repository.SpaceRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceService {

  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;
  private final SharePageRepository sharePageRepository;
  private final SpaceParticipationInfoRepository spaceParticipationInfoRepository;

  /**
   * 기본 페이지가 생성되어 있는 새로운 스페이스 생성
   *
   * @param userId 소유자 ID
   * @param defaultPageId 기본 페이지 ID
   * @return 생성된 스페이스 ID
   */
  @Transactional
  public UUID createSpace(Long userId, UUID defaultPageId) {

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.USER_ENTITY_NOT_FOUND));

    Space generatedSpace = Space.create(user);

    SharePage defaultSharePage =
        sharePageRepository
            .findSharePageByObjectId(defaultPageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    generatedSpace.setMainPage(defaultSharePage);
    spaceRepository.save(generatedSpace);

    defaultSharePage.setParentObjectId(generatedSpace.getSpaceId());
    sharePageRepository.save(defaultSharePage);

    return generatedSpace.getSpaceId();
  }

  @Transactional(readOnly = true)
  public List<ParticipationInfoResponseDTO> getParticipatingInfo(Long userId) {
    List<ParticipationInfoResponseDTO> participationInfoResponseDTOS = new ArrayList<>();

    spaceParticipationInfoRepository
        .findAllByUserId(userId)
        .forEach(
            spaceParticipationInfo -> {
              Space space =
                  spaceRepository.findById(spaceParticipationInfo.getSpaceId()).orElseThrow();
              participationInfoResponseDTOS.add(
                  ParticipationInfoResponseDTO.of(
                      spaceParticipationInfo.getSpaceId(),
                      space.getMainPage().getObjectId(),
                      space.getMainPage().getTitle(),
                      spaceParticipationInfo.getParticipationType()));
            });

    return participationInfoResponseDTOS;
  }

  @Transactional
  public void deleteSpace(UUID spaceId) {

    spaceRepository.deleteById(spaceId);

    log.info("Deleted Space Id : {}", spaceId);
  }

  // TODO : 조회 쿼리 최적화 할 수 있는 방법이 있을까?
  @Transactional(readOnly = true)
  public SharePageTreeResponseDTO.PageTreeNode spaceSharePageTree(UUID spaceId) {
    try {
      SharePage mainPage =
          spaceRepository.findById(spaceId).orElseThrow(EntityNotFoundException::new).getMainPage();

      SharePageTreeResponseDTO.PageTreeNode rootNode =
          SharePageTreeResponseDTO.PageTreeNode.of(
              mainPage.getParentObjectId(), mainPage.getObjectId(), mainPage.getTitle());

      Queue<SharePageTreeResponseDTO.PageTreeNode> nodesQueue = new LinkedList<>();

      nodesQueue.add(rootNode);

      while (!nodesQueue.isEmpty()) {
        SharePageTreeResponseDTO.PageTreeNode currentNode = nodesQueue.poll();
        SharePage currentPage =
            sharePageRepository
                .findSharePageByObjectId(currentNode.pageId())
                .orElseThrow(EntityNotFoundException::new);

        if (currentPage.getChildPages() != null) {
          currentPage
              .getChildPages()
              .forEach(
                  childPage -> {
                    SharePageTreeResponseDTO.PageTreeNode childNode =
                        SharePageTreeResponseDTO.PageTreeNode.of(
                            childPage.getParentObjectId(),
                            childPage.getObjectId(),
                            childPage.getTitle());
                    nodesQueue.add(childNode);
                    currentNode.addChild(childNode);
                  });
        }
      }

      return rootNode;
    } catch (EntityNotFoundException ene) {
      log.info(ene.getMessage());
      throw new ApiClientException(ErrorMessage.ENTITY_NOT_FOUND);
    }
  }

  @Transactional(readOnly = true)
  public SpaceInfoDTO getSpaceInfo(UUID spaceId) {
    return SpaceInfoDTO.fromEntity(
        spaceRepository
            .findById(spaceId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SPACE_ENTITY_NOT_FOUND)));
  }
}
