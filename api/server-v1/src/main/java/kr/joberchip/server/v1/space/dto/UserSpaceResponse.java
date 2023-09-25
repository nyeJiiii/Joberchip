package kr.joberchip.server.v1.space.dto;

import java.util.UUID;
import kr.joberchip.core.space.Space;

public record UserSpaceResponse(UUID spaceId, UUID mainPageId) {
  public static UserSpaceResponse of(UUID spaceId, UUID mainPageId) {
    return new UserSpaceResponse(spaceId, mainPageId);
  }

  public static UserSpaceResponse fromEntity(Space space) {
    return UserSpaceResponse.of(space.getSpaceId(), space.getMainPage().getPageId());
  }
}
