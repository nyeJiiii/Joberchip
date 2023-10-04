package kr.joberchip.core.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "map_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class MapBlock extends BaseObject {

  @Column(name = "address")
  private String address;

  @Column(name = "latitude")
  private Double latitude;

  @Column(name = "longitude")
  private Double longitude;

  public static MapBlock of(
      String address,
      Double latitude,
      Double longitude,
      Integer x,
      Integer y,
      Integer w,
      Integer h) {

    MapBlock generated = new MapBlock(address, latitude, longitude);

    generated.setX(x);
    generated.setY(y);
    generated.setW(w);
    generated.setH(h);

    return generated;
  }
}
