package kr.joberchip.server.v1.domain.block.controller.dto;

import javax.persistence.Lob;
import kr.joberchip.core.block.TextBlock;

public record TextBlockDTO(
    @Lob String content, Integer x, Integer y, Integer w, Integer h, Boolean visible) {
  public TextBlock toEntity() {
    return TextBlock.of(content, x, y, w, h);
  }
}
