package kr.joberchip.server.v1.domain.block.controller.dto;

import kr.joberchip.core.block.ImageBlock;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record ImageBlockDTO(
    @RequestPart String title,
    @RequestPart String description,
    @RequestPart MultipartFile attachedImage,
    @RequestPart Integer x,
    @RequestPart Integer y,
    @RequestPart Integer w,
    @RequestPart Integer h,
    @RequestPart Boolean visible) {

  public ImageBlock toEntity() {
    return ImageBlock.of(title, description, x, y, w, h);
  }
}
