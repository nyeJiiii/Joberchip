package kr.joberchip.core.space.block;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.BaseEntity;
import kr.joberchip.core.space.page.SpacePage;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseBlock extends BaseEntity {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "block_id", columnDefinition = "BINARY(16)")
  private UUID blockId;

  @ManyToOne
  @JoinColumn(name = "page_id")
  private SpacePage parentPage;

  @Column(name = "x_position")
  private Integer x;

  @Column(name = "y_position")
  private Integer y;

  @Column(name = "width")
  private Integer width;

  @Column(name = "height")
  private Integer height;
}
