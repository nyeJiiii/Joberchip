package kr.joberchip.server.v1.share.block.repository;

import java.util.UUID;
import kr.joberchip.core.share.block.TemplateBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateBlockRepository extends JpaRepository<TemplateBlock, UUID> {}
