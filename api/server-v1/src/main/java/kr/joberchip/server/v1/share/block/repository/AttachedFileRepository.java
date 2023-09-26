package kr.joberchip.server.v1.share.block.repository;

import kr.joberchip.core.storage.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {}
