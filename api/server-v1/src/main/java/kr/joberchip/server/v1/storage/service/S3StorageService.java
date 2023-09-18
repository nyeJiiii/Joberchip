package kr.joberchip.server.v1.storage.service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class S3StorageService implements StorageService {
  @Override
  public void init() {}

  @Override
  public void store(MultipartFile file) {}

  @Override
  public Stream<Path> loadAll() {
    return null;
  }

  @Override
  public Path load(String filename) {
    return null;
  }

  @Override
  public Resource loadAsResource(String filename) {
    return null;
  }

  @Override
  public void deleteAll() {}
}
