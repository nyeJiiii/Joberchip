package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.TextBlock;

public record TextBlockDTO(String content) {
  public TextBlock toEntity() {
    return TextBlock.of(content);
  }
}
