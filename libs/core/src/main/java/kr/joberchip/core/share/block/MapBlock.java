package kr.joberchip.core.share.block;

import kr.joberchip.core.share.BaseObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "map_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapBlock extends BaseObject {

  @Column(name = "address")
  @Getter
  private String address;

  @Column(name = "latitude")
  @Getter
  private Double latitude;

  @Column(name = "longitude")
  @Getter
  private Double longitude;

  public UUID getMapBlockId() {
    return this.objectId;
  }
}
