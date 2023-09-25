package kr.joberchip.core.share;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import kr.joberchip.core.BaseEntity;
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
  protected Integer width;

  @Column(name = "height")
  protected Integer height;
}
