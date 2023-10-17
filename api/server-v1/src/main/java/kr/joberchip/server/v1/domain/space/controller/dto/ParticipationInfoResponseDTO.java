package kr.joberchip.server.v1.domain.space.controller.dto;

import java.util.UUID;
import kr.joberchip.core.space.types.ParticipationType;

public record ParticipationInfoResponseDTO(
    UUID spaceId, UUID mainPageId, String mainPageTitle, ParticipationType participationType) {
  public static ParticipationInfoResponseDTO of(
      UUID spaceId, UUID mainPageId, String mainPageTitle, ParticipationType participationType) {
    return new ParticipationInfoResponseDTO(spaceId, mainPageId, mainPageTitle, participationType);
  }
}
