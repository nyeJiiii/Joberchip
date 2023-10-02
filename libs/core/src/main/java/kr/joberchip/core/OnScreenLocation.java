package kr.joberchip.core;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class OnScreenLocation extends BaseEntity {
  @Column(name = "x_pos")
  protected Integer x;

  @Column(name = "y_pos")
  protected Integer y;

  @Column(name = "width")
  protected Integer w;

  @Column(name = "height")
  protected Integer h;
}
