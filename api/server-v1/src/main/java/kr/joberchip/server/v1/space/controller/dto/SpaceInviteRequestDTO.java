package kr.joberchip.server.v1.space.controller.dto;

import java.util.UUID;
import kr.joberchip.core.space.types.ParticipationType;

public record SpaceInviteRequestDTO(Long userId, UUID spaceId) {

  @Override
  public String toString() {
    return "{ " + "userId : " + userId + ", spaceId : " + spaceId + " }";
  }
}
