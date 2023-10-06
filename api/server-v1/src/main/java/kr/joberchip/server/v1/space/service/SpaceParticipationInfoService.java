package kr.joberchip.server.v1.space.service;

import java.util.UUID;
import kr.joberchip.core.space.SpaceParticipationInfo;
import kr.joberchip.core.space.types.ParticipationType;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.space.controller.dto.SpaceInviteRequestDTO;
import kr.joberchip.server.v1.space.repository.SpaceParticipationInfoRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceParticipationInfoService {
  private final UserRepository userRepository;
  private final SpaceParticipationInfoRepository spaceParticipationInfoRepository;

  @Transactional
  public void registerOwnerInfo(Long userId, UUID spaceId) {
    spaceParticipationInfoRepository
        .findByUserIdAndParticipationType(userId, ParticipationType.DEFAULT)
        .ifPresentOrElse(
            spaceParticipationInfo -> {
              spaceParticipationInfoRepository.save(
                  SpaceParticipationInfo.of(userId, spaceId, ParticipationType.OWNER));
              log.info(
                  "Participation info registered : {}, {}, {}",
                  userId,
                  spaceId,
                  ParticipationType.OWNER);
            },
            () -> {
              spaceParticipationInfoRepository.save(
                  SpaceParticipationInfo.of(userId, spaceId, ParticipationType.DEFAULT));
              log.info(
                  "Participation info registered : {}, {}, {}",
                  userId,
                  spaceId,
                  ParticipationType.DEFAULT);
            });
  }

  @Transactional
  public void registerInvitation(SpaceInviteRequestDTO spaceInviteRequestDTO) {
    if (!userRepository.existsById(spaceInviteRequestDTO.userId()))
      throw new ApiClientException(ErrorMessage.USER_ENTITY_NOT_FOUND);

    log.info(
        "Invited User info : {}",
        userRepository.findById(spaceInviteRequestDTO.userId()).orElse(null));

    spaceParticipationInfoRepository
        .findByUserIdAndSpaceId(spaceInviteRequestDTO.userId(), spaceInviteRequestDTO.spaceId())
        .ifPresentOrElse(
            spaceParticipationInfo -> {
              throw new ApiClientException(ErrorMessage.ALREADY_INVITED_USER);
            },
            () -> {
              spaceParticipationInfoRepository.save(
                  SpaceParticipationInfo.of(
                      spaceInviteRequestDTO.userId(),
                      spaceInviteRequestDTO.spaceId(),
                      ParticipationType.PARTICIPANT));
            });

    log.info("[SpaceParticipationInfoService] Invitation registered : {}", spaceInviteRequestDTO);
  }

  @Transactional
  public void deleteParticipationInfo(Long userId, UUID spaceId) {
    spaceParticipationInfoRepository.deleteByUserIdAndSpaceId(userId, spaceId);
  }

  @Transactional(readOnly = true)
  public void checkOwner(Long userId, UUID spaceId) {
    ParticipationType participationType =
        spaceParticipationInfoRepository
            .findByUserIdAndSpaceId(userId, spaceId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.NO_SPACE_PARTICIPATION_INFO))
            .getParticipationType();

    if (participationType != ParticipationType.OWNER) {
      throw new ApiClientException(ErrorMessage.NOT_SPACE_OWNER);
    }

    log.info(
        "[SpaceParticipationInfoService] OWNER checked : userId - {}, spaceId - {}",
        userId,
        spaceId);
  }

  @Transactional(readOnly = true)
  public void checkDefault(Long userId, UUID spaceId) {
    ParticipationType participationType =
        spaceParticipationInfoRepository
            .findByUserIdAndSpaceId(userId, spaceId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.NO_SPACE_PARTICIPATION_INFO))
            .getParticipationType();

    if (participationType == ParticipationType.DEFAULT) {
      throw new ApiClientException(ErrorMessage.DEFAULT_SPACE_CANNOT_REMOVE);
    }

    log.info("[SpaceParticipationInfoService] not default space - {}", spaceId);
  }

  @Transactional(readOnly = true)
  public void checkOwnerOrDefault(Long userId, UUID spaceId) {
    ParticipationType participationType =
        spaceParticipationInfoRepository
            .findByUserIdAndSpaceId(userId, spaceId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.NO_SPACE_PARTICIPATION_INFO))
            .getParticipationType();

    if (participationType != ParticipationType.DEFAULT
        && participationType != ParticipationType.OWNER) {
      throw new ApiClientException(ErrorMessage.NOT_SPACE_OWNER);
    }

    log.info(
        "[SpaceParticipationInfoService] Participation Type checked : userId - {}, spaceId - {}, participation type - {}",
        userId,
        spaceId,
        participationType);
  }
}
