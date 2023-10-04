package kr.joberchip.server.v1.page.controller.dto;

import java.util.UUID;
import kr.joberchip.core.page.SharePage;

public record SharePageRequestDTO(UUID parentPageId, String title, String description) {

  public SharePage toEntity() {
    return SharePage.of(title, description);
  }
}
