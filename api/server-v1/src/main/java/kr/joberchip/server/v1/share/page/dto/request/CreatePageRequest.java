package kr.joberchip.server.v1.share.page.dto.request;

import java.util.UUID;
import kr.joberchip.core.share.page.SharePage;

public record CreatePageRequest(UUID parentPageId, String title, String description) {

  public SharePage toEntity() {
    return SharePage.of(parentPageId, title, description);
  }
}
