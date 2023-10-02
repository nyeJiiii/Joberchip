package kr.joberchip.server.v1.share.block.repository;

import java.util.UUID;
import kr.joberchip.core.share.block.TextBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TextBlockRepository extends JpaRepository<TextBlock, UUID> {
    @Modifying
    @Query(value =
            "UPDATE TextBlock " +
                    "SET content = :newContent, " +
                    "x = :x, y = :y, height = :height, width = :width " +
                    "WHERE objectId = :id")
    void updateAllById(
            @Param("newContent") String newContent,
            @Param("x") Integer x,
            @Param("y") Integer y,
            @Param("height") Integer height,
            @Param("width") Integer width,
            @Param("id") UUID id);
}
