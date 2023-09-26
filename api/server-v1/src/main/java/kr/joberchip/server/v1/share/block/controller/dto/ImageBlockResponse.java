package kr.joberchip.server.v1.share.block.controller.dto;

import java.util.UUID;
import kr.joberchip.core.share.block.ImageBlock;

public record ImageBlockResponse(UUID id, String title, String description, String path) {
  public static ImageBlockResponse fromEntity(ImageBlock imageBlock) {
    return new ImageBlockResponse(
        imageBlock.getImageBlockId(),
        imageBlock.getTitle(),
        imageBlock.getDescription(),
        imageBlock.getImageBlockFile().getAttachedFile().getStoredName());
  }
}
