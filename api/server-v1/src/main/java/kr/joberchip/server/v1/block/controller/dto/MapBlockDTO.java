package kr.joberchip.server.v1.block.controller.dto;

import kr.joberchip.core.block.MapBlock;

public record MapBlockDTO(
    String address,
    Double latitude,
    Double longitude,
    Integer x,
    Integer y,
    Integer w,
    Integer h,
    Boolean visible) {

  public MapBlock toEntity() {
    return MapBlock.of(address, latitude, longitude, x, y, w, h);
  }
}
