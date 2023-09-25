package kr.joberchip.server.v1.share.block.dto.response;

import kr.joberchip.core.share.block.VideoBlock;

public record VideoBlockResponse() {
  public static VideoBlockResponse fromEntity(VideoBlock videoBlock) {
    return new VideoBlockResponse();
  }
}
