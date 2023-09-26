package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.TextBlock;

public record TextBlockResponse() {
  public static TextBlockResponse fromEntity(TextBlock textBlock) {
    return new TextBlockResponse();
  }
}
