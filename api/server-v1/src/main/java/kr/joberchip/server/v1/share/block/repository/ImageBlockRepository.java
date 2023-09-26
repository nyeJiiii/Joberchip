package kr.joberchip.server.v1.share.block.repository;

import java.util.Optional;
import java.util.UUID;
import kr.joberchip.core.share.block.ImageBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageBlockRepository extends JpaRepository<ImageBlock, UUID> {
  Optional<ImageBlock> findByObjectId(UUID id);
}
