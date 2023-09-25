package kr.joberchip.server.v1.share.block.repository;

import kr.joberchip.core.share.block.ImageBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MapBlockRepository extends JpaRepository<ImageBlock, UUID> {}
