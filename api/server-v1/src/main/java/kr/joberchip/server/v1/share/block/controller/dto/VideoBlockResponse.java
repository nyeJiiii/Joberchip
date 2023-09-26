package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.VideoBlock;

public record VideoBlockResponse() {
  public static VideoBlockResponse fromEntity(VideoBlock videoBlock) {
    return new VideoBlockResponse();
  }
}
