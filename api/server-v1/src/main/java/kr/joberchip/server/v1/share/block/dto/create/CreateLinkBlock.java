package kr.joberchip.server.v1.share.block.dto.create;

import kr.joberchip.core.share.block.LinkBlock;

public record CreateLinkBlock(String title, String description, String link) {
  public static CreateLinkBlock of(String title, String description, String link) {
    return new CreateLinkBlock(title, description, link);
  }

  public LinkBlock toEntity() {
    return LinkBlock.of(title, description, link);
  }
}
