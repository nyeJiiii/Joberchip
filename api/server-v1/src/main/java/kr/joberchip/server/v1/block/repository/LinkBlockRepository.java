package kr.joberchip.server.v1.block.repository;

import java.util.UUID;
import kr.joberchip.core.block.LinkBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkBlockRepository extends JpaRepository<LinkBlock, UUID> {}
