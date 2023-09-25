package kr.joberchip.server.v1.share.block.dto.response;

import kr.joberchip.core.share.block.ImageBlock;

public record ImageBlockResponse() {
  public static ImageBlockResponse fromEntity(ImageBlock imageBlock) {
    return new ImageBlockResponse();
  }
}
