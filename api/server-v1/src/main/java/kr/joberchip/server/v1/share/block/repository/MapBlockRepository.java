package kr.joberchip.server.v1.share.block.repository;

import kr.joberchip.core.share.block.MapBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MapBlockRepository extends JpaRepository<MapBlock, UUID> {

    @Modifying
    @Query(value =
            "UPDATE MapBlock " +
            "SET address = :newAddress, latitude = :newLatitude, longitude = :newLongitude " +
            "WHERE objectId = :id")
    void updateById(
            @Param("newAddress") String newAddress,
            @Param("newLatitude") Double newLatitude,
            @Param("newLongitude") Double newLongitude,
            @Param("id") UUID id);

}
