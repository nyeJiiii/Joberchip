package kr.joberchip.server.v1.share.page.dto.response;

import java.util.LinkedHashSet;
import java.util.Set;
import kr.joberchip.core.share.page.SharePage;

public record SharePageDetailResponse(
    String title, String description, Set<BlockObjectResponse> children) {

  public static SharePageDetailResponse fromEntity(SharePage sharePage) {
    SharePageDetailResponse response =
        new SharePageDetailResponse(
            sharePage.getTitle(), sharePage.getDescription(), new LinkedHashSet<>());

    if (sharePage.getChildPages() != null) {
      sharePage.getChildPages().stream()
          .map(BlockObjectResponse::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getTextBlocks() != null) {
      sharePage.getTextBlocks().stream()
          .map(BlockObjectResponse::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getTemplateBlocks() != null) {
      sharePage.getTemplateBlocks().stream()
          .map(BlockObjectResponse::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getLinkBlocks() != null) {
      sharePage.getLinkBlocks().stream()
          .map(BlockObjectResponse::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getImageBlocks() != null) {
      sharePage.getImageBlocks().stream()
          .map(BlockObjectResponse::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getVideoBlocks() != null) {
      sharePage.getVideoBlocks().stream()
          .map(BlockObjectResponse::fromEntity)
          .forEach(response.children::add);
    }

    return response;
  }
}
