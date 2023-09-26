package kr.joberchip.server.v1.share.block.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import kr.joberchip.core.share.block.VideoBlock;
import kr.joberchip.core.storage.AttachedFile;
import kr.joberchip.core.storage.VideoBlockFile;
import kr.joberchip.server.v1.share.block.dto.create.CreateVideoBlock;
import kr.joberchip.server.v1.share.block.dto.modify.ModifyVideoBlock;
import kr.joberchip.server.v1.share.block.repository.AttachedFileRepository;
import kr.joberchip.server.v1.share.block.repository.VideoBlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoBlockService {

  private final VideoBlockRepository videoBlockRepository;

  private final AttachedFileRepository attachedFileRepository;

  @Value("${upload.directory}")
  private String uploadDir;

  @Value("${ffmpeg.executable}")
  private String ffmpegPath;

  public void createVideoBlock(UUID pageId, CreateVideoBlock createVideoBlock) {}

  // 영상 업로드
  public void uploadVideo(CreateVideoBlock createVideoBlock) {
    String originalFileName = createVideoBlock.videoFile().getOriginalFilename();

    String folderName = generateFolderName(originalFileName);
    Path folderPath = Path.of(uploadDir, folderName);

    try {
      if (!Files.exists(folderPath)) {
        Files.createDirectories(folderPath);
      }

      String inputFilePath = folderPath.resolve(originalFileName).toString();
      Files.copy(
          createVideoBlock.videoFile().getInputStream(),
          Paths.get(inputFilePath),
          StandardCopyOption.REPLACE_EXISTING);

      String outputFileName = UUID.randomUUID() + ".mp4";
      String outputFilePath = folderPath.resolve(outputFileName).toString();

      // ffmpeg를 사용하여 확장자를 변경
      String[] ffmpegCommand = {
        ffmpegPath, "-i", inputFilePath, "-c:v", "copy", "-c:a", "aac", outputFilePath
      };

      ProcessBuilder pb = new ProcessBuilder(ffmpegCommand);
      pb.inheritIO().start().waitFor();

      AttachedFile attachedFile = AttachedFile.of("mp4", outputFileName);
      AttachedFile savedAttachedFile = attachedFileRepository.save(attachedFile);

      VideoBlock videoBlock = new VideoBlock();
      videoBlock.modifyTitle(originalFileName);

      VideoBlockFile videoBlockFile = new VideoBlockFile();
      videoBlockFile.setVideoBlock(videoBlock);
      videoBlockFile.setAttachedFile(savedAttachedFile);
      VideoBlock savedVideoBlock = videoBlockRepository.save(videoBlock);

    } catch (IOException e) {
      log.error("영상 업로드 중 오류 발생", e);
    } catch (InterruptedException e) {
      log.error("ffmpeg 프로세스 실행 중 오류 발생", e);
    }
  }

  // 폴더 생성
  private String generateFolderName(String originalFileName) {

    String folderName = originalFileName.replaceAll("[^a-zA-Z0-9]", "_");

    return folderName;
  }

  public void modifyVideoBlock(UUID pageId, UUID blockId, ModifyVideoBlock modifyVideoBlock) {}

  public void deleteVideoBlock(UUID pageId, UUID blockId) {}
}
