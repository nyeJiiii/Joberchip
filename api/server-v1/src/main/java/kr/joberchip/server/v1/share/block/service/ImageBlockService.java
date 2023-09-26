package kr.joberchip.server.v1.share.block.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.share.block.ImageBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.core.storage.AttachedFile;
import kr.joberchip.server.v1.share.block.controller.dto.ImageBlockDTO;
import kr.joberchip.server.v1.share.block.controller.dto.ImageBlockResponse;
import kr.joberchip.server.v1.share.block.repository.AttachedFileRepository;
import kr.joberchip.server.v1.share.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageBlockService {
  private final SharePageRepository sharePageRepository;
  private final AttachedFileRepository attachedFileRepository;

  /*
   public void store(MultipartFile file) {
     try {
       storageService.store(file);
     } catch (StorageException se) {
       log.info("StorageException : {}", se.getMessage());
       log.info("caused by: {}", se.getCause().getMessage());
     }
     log.info("Successfully uploaded : {}", file.getOriginalFilename());
   }
  */
  @Value("${file.dir}")
  private String fileDir;

  public String getFullPath(String filename) {
    return fileDir + filename;
  }

  @Transactional
  public ImageBlockResponse createImageBlock(UUID pageId, ImageBlockDTO imageBlockDTO) {
    SharePage parentPage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);
    ImageBlock imageBlock = imageBlockDTO.toEntity();

    AttachedFile attachedFile = ImageFile(imageBlockDTO.attachedImage());
    // imageBlock = imageBlockRepository.save(imageBlock);

    return ImageBlockResponse.fromEntity(imageBlock);
  }

  private AttachedFile ImageFile(MultipartFile multipartFile) {

    String originalFilename = multipartFile.getOriginalFilename();
    String storeFileName = createStoreFileName(originalFilename);

    try {
      multipartFile.transferTo(new File(getFullPath(storeFileName)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return AttachedFile.of("image", storeFileName);
  }

  private String createStoreFileName(String originalFilename) {
    String ext = extractExt(originalFilename);
    String uuid = UUID.randomUUID().toString();
    return uuid + "." + ext;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

  public void modifyImageBlock(UUID pageId, Long blockId, ImageBlockDTO imageBlockDTO) {}

  public void deleteImageBlock(UUID pageId, Long blockId) {}
}
