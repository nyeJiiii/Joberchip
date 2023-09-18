package kr.joberchip.server.v1.space.block.repository;

import java.util.UUID;
import kr.joberchip.core.space.block.VideoBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoBlockRepository extends JpaRepository<VideoBlock, UUID> {}
