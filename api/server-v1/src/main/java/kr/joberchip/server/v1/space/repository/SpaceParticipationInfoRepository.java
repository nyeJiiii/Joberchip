package kr.joberchip.server.v1.space.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kr.joberchip.core.space.SpaceParticipationInfo;
import kr.joberchip.core.space.types.ParticipationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceParticipationInfoRepository
    extends JpaRepository<SpaceParticipationInfo, Long> {
  List<SpaceParticipationInfo> findAllByUserId(Long userId);

  void deleteBySpaceId(UUID spaceId);

  Optional<SpaceParticipationInfo> findByUserIdAndParticipationType(
      Long userId, ParticipationType participationType);

  boolean existsByUserIdAndParticipationType(Long userId, ParticipationType participationType);

  boolean existsByUserIdAndSpaceId(Long userId, UUID spaceId);

  Optional<SpaceParticipationInfo> findByUserIdAndSpaceId(Long userId, UUID spaceId);
  Optional<SpaceParticipationInfo> findByUserIdAndSpaceIdAndParticipationType(Long userId, UUID spaceId, ParticipationType participationType);
  void deleteByUserIdAndSpaceId(Long userId, UUID spaceId);

}
