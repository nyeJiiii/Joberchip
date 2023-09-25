package kr.joberchip.server.v1.share.block.dto.create;

import kr.joberchip.core.share.block.MapBlock;
import lombok.Getter;

@Getter
public class CreateMapBlock {

    public String address;
    public Double latitude;
    public Double longitude;

    public MapBlock toEntity() {
        return MapBlock.builder()
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
