package kr.joberchip.server.v1.share.block.dto.response;

import kr.joberchip.core.share.block.LinkBlock;

public record LinkBlockResponse() {
  public static LinkBlockResponse fromEntity(LinkBlock linkBlock) {
    return new LinkBlockResponse();
  }
}
