package kr.joberchip.server.v1.user.repository;

import java.util.Optional;
import java.util.UUID;

import kr.joberchip.core.user.PagePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagePermissionRepository extends JpaRepository<PagePermission, Long> {
  Optional<PagePermission> findByUserId(Long userId);
  Optional<PagePermission> findByUserIdAndPageId(Long userId, UUID pageId);
}
