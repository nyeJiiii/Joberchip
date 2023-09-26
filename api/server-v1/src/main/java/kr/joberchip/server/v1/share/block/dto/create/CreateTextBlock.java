package kr.joberchip.server.v1.share.block.dto.create;

import kr.joberchip.core.share.block.TextBlock;

public record CreateTextBlock(String content) {
  public TextBlock toEntity() {
    return TextBlock.of(content);
  }
}
