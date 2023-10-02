package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateMapBlock {

    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private String address;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Double latitude;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Double longitude;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer x;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer y;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer height;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer width;

    public MapBlock setEntity(MapBlock mapBlock) {
        mapBlock.setAddress(this.getAddress());
        mapBlock.setLatitude(this.getLatitude());
        mapBlock.setLongitude(this.getLongitude());
        mapBlock.setX(this.getX());
        mapBlock.setY(this.getY());
        mapBlock.setHeight(this.getHeight());
        mapBlock.setWidth(this.getWidth());
        return mapBlock;
    }

}
