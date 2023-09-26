package kr.joberchip.server.v1.share.block.dto.create;

import kr.joberchip.core.share.block.ImageBlock;
import org.springframework.web.multipart.MultipartFile;

public record CreateImageBlock(String title, String description, MultipartFile attachedImage) {
  public static CreateImageBlock of(String title, String description, MultipartFile attachedImage) {
    return new CreateImageBlock(title, description, attachedImage);
  }
  public ImageBlock toEntity() {
    return ImageBlock.of(title, description);
  }
}
