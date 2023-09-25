package kr.joberchip.server.v1.share.page.repository;

import java.util.UUID;
import kr.joberchip.core.share.page.SharePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharePageRepository extends JpaRepository<SharePage, UUID> {}
