package kr.joberchip.server.v1.space.service;

import java.util.UUID;
import kr.joberchip.core.space.Space;

public record SpaceInfoDTO(UUID spaceId, UUID mainPageId, Long creatorId) {
  public static SpaceInfoDTO fromEntity(Space space) {
    return new SpaceInfoDTO(
        space.getSpaceId(), space.getMainPage().getObjectId(), space.getCreator().getUserId());
  }
}
