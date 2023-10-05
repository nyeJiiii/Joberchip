package kr.joberchip.server.v1.block.controller.dto;

import java.util.UUID;
import kr.joberchip.core.block.LinkBlock;

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

  public LinkBlock toEntity() {
    return LinkBlock.of(title, description, link, x, y, w, h, visible);
  }
}
