package kr.joberchip.server.v1.share.block.repository;

import kr.joberchip.core.share.block.MapBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface MapBlockRepository extends JpaRepository<MapBlock, UUID> {

//    @Modifying
//    @Query(value =
//            "UPDATE MapBlock " +
//            "SET address = :newAddress, latitude = :newLatitude, longitude = :newLongitude, " +
//                    "x = :x, y = :y, height = :height, width = :width " +
//            "WHERE objectId = :id")
//    void updateAllById(
//            @Param("newAddress") String newAddress,
//            @Param("newLatitude") Double newLatitude,
//            @Param("newLongitude") Double newLongitude,
//            @Param("x") Integer x,
//            @Param("y") Integer y,
//            @Param("height") Integer height,
//            @Param("width") Integer width,
//            @Param("id") UUID id);

}
