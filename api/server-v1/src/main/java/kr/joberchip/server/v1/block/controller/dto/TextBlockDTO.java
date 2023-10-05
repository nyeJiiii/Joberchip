package kr.joberchip.server.v1.block.controller.dto;

import kr.joberchip.core.block.TextBlock;

public record TextBlockDTO(
    String content, Integer x, Integer y, Integer w, Integer h, Boolean visible) {
  public TextBlock toEntity() {
    return TextBlock.of(content, x, y, w, h);
  }
}
