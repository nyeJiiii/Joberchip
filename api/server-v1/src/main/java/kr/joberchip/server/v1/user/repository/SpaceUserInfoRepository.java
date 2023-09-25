package kr.joberchip.server.v1.user.repository;

import java.util.List;
import java.util.Optional;
import kr.joberchip.core.space.ParticipationType;
import kr.joberchip.core.user.SpaceUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceUserInfoRepository extends JpaRepository<SpaceUserInfo, Long> {
  List<SpaceUserInfo> findAllByUser_UserId(Long userId);

  Optional<SpaceUserInfo> findByUser_UserIdAndParticipationType(
      Long userId, ParticipationType participationType);

  boolean existsByUser_UserIdAndParticipationType(Long userId, ParticipationType participationType);
}
