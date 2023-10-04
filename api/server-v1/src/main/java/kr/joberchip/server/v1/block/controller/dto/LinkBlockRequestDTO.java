package kr.joberchip.server.v1.block.controller.dto;

import java.util.UUID;
import kr.joberchip.core.block.LinkBlock;
import org.springframework.web.bind.annotation.RequestParam;

public record LinkBlockRequestDTO(
    UUID pageId,
    String title,
    String description,
    String link,
    Integer x,
    Integer y,
    Integer w,
    Integer h,
    Boolean visible) {

//  public static LinkBlockRequestDTO of(UUID pageId, String title, String description, String link, Boolean visible) {
//    return new LinkBlockRequestDTO(pageId, title, description, link, 0, 0, 2, 2, visible);
//  }

  public LinkBlock toEntity() {
    return LinkBlock.of(title, description, link, x, y, w, h, visible);
  }
}
