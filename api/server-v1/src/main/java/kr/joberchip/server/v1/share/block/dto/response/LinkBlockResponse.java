package kr.joberchip.server.v1.share.block.dto.response;

import kr.joberchip.core.share.block.LinkBlock;

public record LinkBlockResponse(String title, String description, String link) {

  public static LinkBlockResponse fromEntity(LinkBlock linkBlock) {
    return new LinkBlockResponse(
        linkBlock.getTitle(), linkBlock.getDescription(), linkBlock.getLink());
  }
}
