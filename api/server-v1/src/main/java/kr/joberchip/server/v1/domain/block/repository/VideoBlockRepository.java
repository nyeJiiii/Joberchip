package kr.joberchip.server.v1.domain.block.repository;

import java.util.UUID;
import kr.joberchip.core.block.VideoBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoBlockRepository extends JpaRepository<VideoBlock, UUID> {}
