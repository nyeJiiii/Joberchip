package kr.joberchip.server.v1.space.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
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
    if (spaceParticipationInfoRepository.existsByUserIdAndParticipationType(
        userId, ParticipationType.DEFAULT)) {

      spaceParticipationInfoRepository.save(
          SpaceParticipationInfo.of(userId, spaceId, ParticipationType.OWNER));

      log.info("Owner Participation info registered : {}, {}", userId, spaceId);

      return;
    }

    spaceParticipationInfoRepository.save(
        SpaceParticipationInfo.of(userId, spaceId, ParticipationType.DEFAULT));

    log.info("Default Participation info registered : {}, {}", userId, spaceId);
  }

  @Transactional
  public void registerInvitation(Long requesterId, SpaceInviteRequestDTO spaceInviteRequestDTO) {
    log.info("Request User Id : {}", requesterId);

    log.info("Requester User info : {}", userRepository.findById(requesterId).orElseThrow());
    log.info(
        "Invited User info : {}",
        userRepository.findById(spaceInviteRequestDTO.userId()).orElseThrow());

    ParticipationType participationType =
        spaceParticipationInfoRepository
            .findByUserIdAndSpaceId(requesterId, spaceInviteRequestDTO.spaceId())
            .orElseThrow(EntityNotFoundException::new)
            .getParticipationType();

    if (participationType == ParticipationType.PARTICIPANT)
      throw new ApiClientException(ErrorMessage.FORBIDDEN);

    if (spaceParticipationInfoRepository.existsByUserIdAndSpaceId(
        spaceInviteRequestDTO.userId(), spaceInviteRequestDTO.spaceId())) {
      throw new ApiClientException("Already invited user");
    }

    spaceParticipationInfoRepository.save(
        SpaceParticipationInfo.of(
            spaceInviteRequestDTO.userId(),
            spaceInviteRequestDTO.spaceId(),
            ParticipationType.PARTICIPANT));

    log.info("Invitation registered : {}", spaceInviteRequestDTO);
  }
}
