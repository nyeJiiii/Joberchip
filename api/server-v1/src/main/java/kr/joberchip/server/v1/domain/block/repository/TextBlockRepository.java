package kr.joberchip.server.v1.domain.block.repository;

import java.util.UUID;
import kr.joberchip.core.block.TextBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextBlockRepository extends JpaRepository<TextBlock, UUID> {}
