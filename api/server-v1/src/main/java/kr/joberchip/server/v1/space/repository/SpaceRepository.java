package kr.joberchip.server.v1.space.repository;

import java.util.Optional;
import java.util.UUID;
import kr.joberchip.core.space.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {
  Optional<Space> findByCreator_UserIdAndMainPageNull(Long creatorId);
  Optional<Space> findByCreator_UserId(Long creatorId);
}
