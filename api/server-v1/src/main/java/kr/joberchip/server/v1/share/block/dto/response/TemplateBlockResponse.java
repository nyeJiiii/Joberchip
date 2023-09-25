package kr.joberchip.server.v1.share.block.dto.response;

import kr.joberchip.core.share.block.TemplateBlock;

public record TemplateBlockResponse() {
  public static TemplateBlockResponse fromEntity(TemplateBlock templateBlock) {
    return new TemplateBlockResponse();
  }
}
