package kr.joberchip.server.v1.block.controller.dto;

import kr.joberchip.core.block.ImageBlock;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record ImageBlockDTO(
    @RequestParam String title,
    @RequestParam String description,
    @RequestPart MultipartFile attachedImage,
    @RequestParam Integer x,
    @RequestParam Integer y,
    @RequestParam Integer w,
    @RequestParam Integer h,
    @RequestParam Boolean visible) {

  public ImageBlock toEntity() {
    return ImageBlock.of(title, description, x, y, w, h, visible);
  }
}
