package kr.joberchip.server.v1.block.controller.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import kr.joberchip.core.block.LinkBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;

public record LinkBlockRequestDTO(
    UUID pageId,
    String title,
    String description,
    @NotNull(message = ErrorMessage.NOT_EMPTY) String link,
    @NotNull(message = ErrorMessage.NOT_EMPTY) Integer x,
    @NotNull(message = ErrorMessage.NOT_EMPTY) Integer y,
    @NotNull(message = ErrorMessage.NOT_EMPTY) Integer w,
    @NotNull(message = ErrorMessage.NOT_EMPTY) Integer h) {

  public static LinkBlockRequestDTO of(UUID pageId, String title, String description, String link) {
    return new LinkBlockRequestDTO(pageId, title, description, link, 0, 0, 2, 2);
  }

  public LinkBlock toEntity() {
    return LinkBlock.of(title, description, link, x, y, w, h);
  }
}
