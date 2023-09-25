package kr.joberchip.server.v1.share.page.dto;

import java.util.UUID;
import kr.joberchip.core.share.page.SharePage;

public record SharePageSummaryResponse(UUID pageId, String title, String description) {
  public static SharePageSummaryResponse fromEntity(SharePage sharePage) {
    return new SharePageSummaryResponse(
        sharePage.getPageId(), sharePage.getTitle(), sharePage.getDescription());
  }
}
