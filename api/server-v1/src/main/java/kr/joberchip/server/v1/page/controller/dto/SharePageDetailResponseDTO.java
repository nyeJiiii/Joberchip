package kr.joberchip.server.v1.page.controller.dto;

import java.util.LinkedHashSet;
import java.util.Set;
import kr.joberchip.core.BaseObject;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;

public record SharePageDetailResponseDTO(
    String title, String description, PrivilegeType privilege, Set<BlockResponseDTO> children) {
  public static SharePageDetailResponseDTO fromEntityWithPrivilege(
      SharePage sharePage, PrivilegeType privilege) {
    SharePageDetailResponseDTO response =
        new SharePageDetailResponseDTO(
            sharePage.getTitle(), sharePage.getDescription(), privilege, new LinkedHashSet<>());

    if (privilege == PrivilegeType.EDIT) {
      addAllChildren(response, sharePage);
    } else {
      addVisibleChildren(response, sharePage);
    }

    return response;
  }

  // 비공개 블럭 제외
  public static SharePageDetailResponseDTO fromEntity(SharePage sharePage) {
    SharePageDetailResponseDTO response =
        new SharePageDetailResponseDTO(
            sharePage.getTitle(), sharePage.getDescription(), null, new LinkedHashSet<>());

    addVisibleChildren(response, sharePage);

    return response;
  }

  private static void addVisibleChildren(SharePageDetailResponseDTO response, SharePage sharePage) {
    if (sharePage.getChildPages() != null) {
      sharePage.getChildPages().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getTextBlocks() != null) {
      sharePage.getTextBlocks().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getTemplateBlocks() != null) {
      sharePage.getTemplateBlocks().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getLinkBlocks() != null) {
      sharePage.getLinkBlocks().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getMapBlocks() != null) {
      sharePage.getMapBlocks().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getImageBlocks() != null) {
      sharePage.getImageBlocks().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getVideoBlocks() != null) {
      sharePage.getVideoBlocks().stream()
          .filter(BaseObject::getVisible)
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }
  }

  private static void addAllChildren(SharePageDetailResponseDTO response, SharePage sharePage) {
    if (sharePage.getChildPages() != null) {
      sharePage.getChildPages().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getTextBlocks() != null) {
      sharePage.getTextBlocks().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getTemplateBlocks() != null) {
      sharePage.getTemplateBlocks().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getLinkBlocks() != null) {
      sharePage.getLinkBlocks().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getMapBlocks() != null) {
      sharePage.getMapBlocks().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getImageBlocks() != null) {
      sharePage.getImageBlocks().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }

    if (sharePage.getVideoBlocks() != null) {
      sharePage.getVideoBlocks().stream()
          .map(BlockResponseDTO::fromEntity)
          .forEach(response.children::add);
    }
  }
}
