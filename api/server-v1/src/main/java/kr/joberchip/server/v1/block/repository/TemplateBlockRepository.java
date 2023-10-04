package kr.joberchip.server.v1.block.repository;

import java.util.UUID;
import kr.joberchip.core.block.TemplateBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateBlockRepository extends JpaRepository<TemplateBlock, UUID> {}
