package kr.joberchip.server.v1.page.repository;

import java.util.Optional;
import java.util.UUID;
import kr.joberchip.core.page.SharePagePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharePagePrivilegeRepository extends JpaRepository<SharePagePrivilege, Long> {
  Optional<SharePagePrivilege> findByUserId(Long userId);

  Optional<SharePagePrivilege> findByUserIdAndSharePageId(Long userId, UUID pageId);

  void deleteAllBySharePageId(UUID pageId);

  boolean existsByUserIdAndSharePageId(Long userId, UUID pageId);
}
