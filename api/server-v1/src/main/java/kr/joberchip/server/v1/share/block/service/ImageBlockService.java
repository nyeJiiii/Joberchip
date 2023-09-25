package kr.joberchip.server.v1.share.block.service;

import kr.joberchip.server.v1._errors.exceptions.StorageException;
import kr.joberchip.server.v1.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageBlockService {
  private final StorageService storageService;

  public void store(MultipartFile file) {
    try {
      storageService.store(file);
    } catch (StorageException se) {
      log.info("StorageException : {}", se.getMessage());
      log.info("caused by: {}", se.getCause().getMessage());
    }
    log.info("Successfully uploaded : {}", file.getOriginalFilename());
  }
}
