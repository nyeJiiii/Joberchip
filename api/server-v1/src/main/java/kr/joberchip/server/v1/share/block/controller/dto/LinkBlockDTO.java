package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.LinkBlock;

public record LinkBlockDTO(String title, String description, String link) {
  public static LinkBlockDTO of(String title, String description, String link) {
    return new LinkBlockDTO(title, description, link);
  }

  public LinkBlock toEntity() {
    return LinkBlock.of(title, description, link);
  }
}
