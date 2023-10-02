package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


public class MapBlockDTO {

    @Getter
    public static class Create {
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

        public MapBlock setEntity (MapBlock mapBlock){
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

    @Getter
    public static class Modify {
        private String address;
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @NoArgsConstructor
    public static class ReturnVisible {
        private Boolean visible;

        public ReturnVisible(MapBlock mapBlock) {
            this.visible = mapBlock.getVisible();
        }
    }

}
