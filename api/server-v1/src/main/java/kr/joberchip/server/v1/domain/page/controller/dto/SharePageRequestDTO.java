package kr.joberchip.server.v1.domain.page.controller.dto;

import java.util.UUID;
import kr.joberchip.core.page.SharePage;

public record SharePageRequestDTO(
    UUID parentPageId,
    String title,
    String description,
    Integer x,
    Integer y,
    Integer w,
    Integer h) {

  public SharePage toEntity() {
    return SharePage.of(title, description, x, y, w, h);
  }
}
