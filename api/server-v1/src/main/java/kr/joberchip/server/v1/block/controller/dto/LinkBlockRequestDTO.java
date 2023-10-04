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
    Integer h) {

  public static LinkBlockRequestDTO of(UUID pageId, String title, String description, String link) {
    return new LinkBlockRequestDTO(pageId, title, description, link, 0, 0, 2, 2);
  }

  public LinkBlock toEntity() {
    return LinkBlock.of(title, description, link, x, y, w, h);
  }
}
