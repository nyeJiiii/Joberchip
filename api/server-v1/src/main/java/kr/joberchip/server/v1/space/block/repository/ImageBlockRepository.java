package kr.joberchip.server.v1.space.block.repository;

import java.util.UUID;
import kr.joberchip.core.space.block.ImageBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageBlockRepository extends JpaRepository<ImageBlock, UUID> {}
