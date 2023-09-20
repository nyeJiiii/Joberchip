package kr.joberchip.core.space;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.space.page.SpacePage;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseObject extends OnScreenLocation {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "object_id", columnDefinition = "BINARY(16)")
  protected UUID objectId;

  @ManyToOne
  @JoinColumn(name = "page_id")
  protected SpacePage parentPage;

  @Column(name = "visible")
  protected Boolean visible = Boolean.TRUE;
}
