package kr.joberchip.server.v1.block.controller.dto;

import javax.validation.constraints.NotNull;
import kr.joberchip.core.block.MapBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Integer w;

    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer h;

    public MapBlock toEntity() {
      return MapBlock.of(address, latitude, longitude, x, y, w, h);
    }
  }

  @Getter
  public static class Modify {
    private String address;
    private Double latitude;
    private Double longitude;
    private Boolean visible;
  }
}
