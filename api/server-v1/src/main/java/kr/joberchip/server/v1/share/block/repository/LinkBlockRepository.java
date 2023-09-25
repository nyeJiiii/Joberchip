package kr.joberchip.server.v1.share.block.repository;

import java.util.UUID;
import kr.joberchip.core.share.block.LinkBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkBlockRepository extends JpaRepository<LinkBlock, UUID> {}
