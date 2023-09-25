package kr.joberchip.server.v1.space.dto;

import java.util.UUID;
import kr.joberchip.core.space.ParticipationType;
import kr.joberchip.core.user.SpaceUserInfo;

public record ParticipatingInfo(
    UUID spaceId, UUID mainPageId, ParticipationType participationType) {

  public static ParticipatingInfo fromEntity(SpaceUserInfo spaceUserInfo) {
    return new ParticipatingInfo(
        spaceUserInfo.getSpace().getSpaceId(),
        spaceUserInfo.getSpace().getMainPage().getPageId(),
        spaceUserInfo.getParticipationType());
  }
}
