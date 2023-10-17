package kr.joberchip.server.v1.domain.block.controller.dto;

import kr.joberchip.core.block.VideoBlock;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;

public record VideoBlockDTO(
    @RequestPart String title,
    @RequestPart String description,
    @RequestPart @Lob String videoLink,
    @RequestPart MultipartFile attachedVideo,
    @RequestPart Integer x,
    @RequestPart Integer y,
    @RequestPart Integer w,
    @RequestPart Integer h,
    @RequestPart Boolean visible) {

  public VideoBlock toEntity() {
    if (videoLink == null || "".equalsIgnoreCase(videoLink))
      return VideoBlock.of(title, description, x, y, w, h);

    return VideoBlock.of(title, description, videoLink, x, y, w, h);
  }
}
