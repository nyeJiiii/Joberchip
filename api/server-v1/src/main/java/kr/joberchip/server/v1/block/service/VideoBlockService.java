package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.block.VideoBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.VideoBlockDTO;
import kr.joberchip.server.v1.block.repository.VideoBlockRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import kr.joberchip.server.v1.storage.service.S3StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoBlockService {

  private final VideoBlockRepository videoBlockRepository;
  private final SharePageRepository sharePageRepository;
  private final S3StorageService s3StorageService;

  public BlockResponseDTO createVideoBlock(UUID pageId, VideoBlockDTO videoBlockDTO) {

    SharePage parentPage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    VideoBlock videoBlock = videoBlockDTO.toEntity();

    if (videoBlockDTO.attachedVideo() != null) {
      String videoLink = s3StorageService.store(videoBlockDTO.attachedVideo());
      videoBlock.setVideoLink(videoLink);
    }

    videoBlockRepository.save(videoBlock);

    parentPage.addVideoBlock(videoBlock);

    return BlockResponseDTO.fromEntity(videoBlock);
  }

  public BlockResponseDTO modifyVideoBlock(UUID pageId, UUID blockId, VideoBlockDTO videoBlockDTO) {
    VideoBlock videoBlock =
        videoBlockRepository.findById(blockId).orElseThrow(EntityNotFoundException::new);

    if (videoBlockDTO.title() != null) videoBlock.setTitle(videoBlockDTO.title());
    if (videoBlockDTO.description() != null) videoBlock.setDescription(videoBlockDTO.description());
    if (videoBlockDTO.attachedVideo() != null) {
      s3StorageService.delete(videoBlock.getVideoLink());
      videoBlock.setVideoLink(s3StorageService.store(videoBlockDTO.attachedVideo()));
    }
    if (videoBlockDTO.visible() != null) videoBlock.setVisible(videoBlockDTO.visible());

    videoBlockRepository.save(videoBlock);

    return BlockResponseDTO.fromEntity(videoBlock);
  }

  public void deleteVideoBlock(UUID pageId, UUID blockId) {
    SharePage sharePage =
        sharePageRepository
            .findSharePageByObjectId(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.ENTITY_NOT_FOUND));

    log.info("[ImageBlockController] Current SharePage : {}", sharePage);

    VideoBlock videoBlock =
        videoBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.ENTITY_NOT_FOUND));

    s3StorageService.delete(videoBlock.getVideoLink());

    sharePage.getVideoBlocks().remove(videoBlock);

    videoBlockRepository.delete(videoBlock);
  }

  // 영상 업로드
  //  public void uploadVideo(VideoBlockRequestDTO videoBlockRequestDTO) {
  //    String originalFileName = videoBlockRequestDTO.videoFile().getOriginalFilename();
  //
  //    String folderName = generateFolderName(originalFileName);
  //    Path folderPath = Path.of(uploadDir, folderName);
  //
  //    try {
  //      if (!Files.exists(folderPath)) {
  //        Files.createDirectories(folderPath);
  //      }
  //
  //      String inputFilePath = folderPath.resolve(originalFileName).toString();
  //      Files.copy(
  //          videoBlockRequestDTO.videoFile().getInputStream(),
  //          Paths.get(inputFilePath),
  //          StandardCopyOption.REPLACE_EXISTING);
  //
  //      String outputFileName = UUID.randomUUID() + ".mp4";
  //      String outputFilePath = folderPath.resolve(outputFileName).toString();
  //
  //      // ffmpeg를 사용하여 확장자를 변경
  //      String[] ffmpegCommand = {
  //        ffmpegPath, "-i", inputFilePath, "-c:v", "copy", "-c:a", "aac", outputFilePath
  //      };
  //
  //      ProcessBuilder pb = new ProcessBuilder(ffmpegCommand);
  //      pb.inheritIO().start().waitFor();
  //
  //      AttachedFile attachedFile = AttachedFile.of(ContentType.MP4, outputFileName);
  //      AttachedFile savedAttachedFile = attachedFileRepository.save(attachedFile);
  //
  //      VideoBlock videoBlock = new VideoBlock();
  //      videoBlock.modifyTitle(originalFileName);
  //
  //      VideoBlockFile videoBlockFile = new VideoBlockFile();
  //      videoBlockFile.setVideoBlock(videoBlock);
  //      videoBlockFile.setAttachedFile(savedAttachedFile);
  //      VideoBlock savedVideoBlock = videoBlockRepository.save(videoBlock);
  //
  //    } catch (IOException e) {
  //      log.error("영상 업로드 중 오류 발생", e);
  //    } catch (InterruptedException e) {
  //      log.error("ffmpeg 프로세스 실행 중 오류 발생", e);
  //    }
  //  }

}
