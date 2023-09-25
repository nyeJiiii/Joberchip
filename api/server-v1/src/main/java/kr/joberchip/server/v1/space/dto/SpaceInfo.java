package kr.joberchip.server.v1.space.dto;

import java.util.UUID;
import kr.joberchip.core.space.Space;

public record SpaceInfo(UUID spaceId, UUID mainPageId) {
  public static SpaceInfo fromEntity(Space space) {
    return new SpaceInfo(space.getSpaceId(), space.getMainPage().getPageId());
  }
}
