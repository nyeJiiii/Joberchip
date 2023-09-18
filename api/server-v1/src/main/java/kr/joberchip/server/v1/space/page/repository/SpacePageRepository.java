package kr.joberchip.server.v1.space.page.repository;

import java.util.UUID;
import kr.joberchip.core.space.page.SpacePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpacePageRepository extends JpaRepository<SpacePage, UUID> {}
