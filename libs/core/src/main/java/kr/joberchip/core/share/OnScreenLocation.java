package kr.joberchip.core.share;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import kr.joberchip.core.BaseEntity;

@MappedSuperclass
public abstract class OnScreenLocation extends BaseEntity {
  @Column(name = "x_position")
  private Integer x;

  @Column(name = "y_position")
  private Integer y;

  @Column(name = "width")
  private Integer width;

  @Column(name = "height")
  private Integer height;
}
