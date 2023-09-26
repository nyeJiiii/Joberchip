package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.ImageBlock;
import org.springframework.web.multipart.MultipartFile;

public record ImageBlockDTO(String title, String description, MultipartFile attachedImage) {
  public static ImageBlockDTO of(String title, String description, MultipartFile attachedImage) {
    return new ImageBlockDTO(title, description, attachedImage);
  }
  public ImageBlock toEntity() {
    return ImageBlock.of(title, description);
  }
}
