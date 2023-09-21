package kr.joberchip.server.v1.space;

import java.util.UUID;
import kr.joberchip.core.space.JoberSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoberSpaceRepository extends JpaRepository<JoberSpace, UUID> {}
