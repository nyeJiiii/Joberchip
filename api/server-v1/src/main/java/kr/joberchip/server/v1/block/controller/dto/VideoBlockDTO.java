package kr.joberchip.server.v1.block.controller.dto;

import kr.joberchip.core.block.VideoBlock;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record VideoBlockDTO(
    @RequestParam String title,
    @RequestParam String description,
    @RequestParam String videoLink,
    @RequestPart MultipartFile attachedVideo,
    @RequestParam Integer x,
    @RequestParam Integer y,
    @RequestParam Integer w,
    @RequestParam Integer h) {

  public VideoBlock toEntity() {
    if ("".equals(videoLink)) return VideoBlock.of(title, description);

    return VideoBlock.of(title, description, videoLink);
  }
}
