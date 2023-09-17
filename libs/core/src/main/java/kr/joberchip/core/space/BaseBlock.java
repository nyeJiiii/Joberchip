package kr.joberchip.core.space;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.space.page.SpacePage;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseBlock extends OnScreenLocation {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "block_id", columnDefinition = "BINARY(16)")
  private UUID blockId;

  @ManyToOne
  @JoinColumn(name = "page_id")
  private SpacePage parentPage;
}
