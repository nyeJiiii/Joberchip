package kr.joberchip.server.v1.share.block.dto.create;

import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateMapBlock {

    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
    public String address;
    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
    public Double latitude;
    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
    public Double longitude;

    public MapBlock toEntity() {
        return MapBlock.builder()
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
