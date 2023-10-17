package kr.joberchip.server.v1.domain.block.service;

import java.util.UUID;
import kr.joberchip.core.block.VideoBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1.global.errors.ErrorMessage;
import kr.joberchip.server.v1.global.errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.domain.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.domain.block.controller.dto.VideoBlockDTO;
import kr.joberchip.server.v1.domain.block.repository.VideoBlockRepository;
import kr.joberchip.server.v1.domain.page.repository.SharePageRepository;
import kr.joberchip.server.v1.domain.storage.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoBlockService {

  private final VideoBlockRepository videoBlockRepository;
  private final SharePageRepository sharePageRepository;
  private final S3StorageService s3StorageService;

  @Transactional
  public BlockResponseDTO createVideoBlock(UUID pageId, VideoBlockDTO videoBlockDTO) {
    if (videoBlockDTO.videoLink() == null && videoBlockDTO.attachedVideo() == null)
      throw new ApiClientException(ErrorMessage.INVALID_VIDEO_BLOCK_REQUEST);

    if (videoBlockDTO.videoLink() != null && videoBlockDTO.attachedVideo() != null)
      throw new ApiClientException(ErrorMessage.DUPLICATED_LINK_AND_ATTACHED_FILE);

    SharePage parentPage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    VideoBlock videoBlock = videoBlockDTO.toEntity();

    if (videoBlockDTO.attachedVideo() != null) {
      String videoLink = s3StorageService.store(videoBlockDTO.attachedVideo());
      videoBlock.setVideoLink(videoLink);
    }

    videoBlockRepository.save(videoBlock);

    parentPage.addVideoBlock(videoBlock);

    return BlockResponseDTO.fromEntity(videoBlock);
  }

  @Transactional
  public BlockResponseDTO modifyVideoBlock(UUID blockId, VideoBlockDTO videoBlockDTO) {
    if (videoBlockDTO.videoLink() == null && videoBlockDTO.attachedVideo() == null)
      throw new ApiClientException(ErrorMessage.INVALID_VIDEO_BLOCK_REQUEST);

    if (videoBlockDTO.videoLink() != null && videoBlockDTO.attachedVideo() != null)
      throw new ApiClientException(ErrorMessage.DUPLICATED_LINK_AND_ATTACHED_FILE);

    VideoBlock videoBlock =
        videoBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    if (videoBlockDTO.title() != null) videoBlock.setTitle(videoBlockDTO.title());
    if (videoBlockDTO.description() != null) videoBlock.setDescription(videoBlockDTO.description());

    if (videoBlockDTO.videoLink() != null) {
      s3StorageService.delete(videoBlock.getVideoLink());
      videoBlock.setVideoLink(videoBlockDTO.videoLink());
    }

    if (videoBlockDTO.attachedVideo() != null) {
      s3StorageService.delete(videoBlock.getVideoLink());
      videoBlock.setVideoLink(s3StorageService.store(videoBlockDTO.attachedVideo()));
    }

    if (videoBlockDTO.visible() != null) videoBlock.setVisible(videoBlockDTO.visible());

    videoBlockRepository.save(videoBlock);

    return BlockResponseDTO.fromEntity(videoBlock);
  }

  @Transactional
  public void deleteVideoBlock(UUID pageId, UUID blockId) {
    SharePage sharePage =
        sharePageRepository
            .findSharePageByObjectId(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    log.info("[ImageBlockController] Current SharePage : {}", sharePage);

    VideoBlock videoBlock =
        videoBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    s3StorageService.delete(videoBlock.getVideoLink());

    sharePage.getVideoBlocks().remove(videoBlock);

    videoBlockRepository.delete(videoBlock);
  }
}
