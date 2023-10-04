package kr.joberchip.server.v1.page.repository;

import java.util.Optional;
import java.util.UUID;
import kr.joberchip.core.page.SharePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharePageRepository extends JpaRepository<SharePage, UUID> {
  Optional<SharePage> findSharePageByObjectId(UUID pageId);
}
