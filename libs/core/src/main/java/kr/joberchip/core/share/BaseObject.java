package kr.joberchip.core.share;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class BaseObject extends OnScreenLocation {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "object_id", columnDefinition = "BINARY(16)")
  protected UUID objectId;

  @Column(name = "parent_object_id", columnDefinition = "BINARY(16)")
  @Setter
  protected UUID parentObjectId;

  @Column(name = "visible")
  @Setter
  @Getter
  protected Boolean visible = Boolean.TRUE;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BaseObject that = (BaseObject) o;
    return Objects.equals(objectId, that.objectId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(objectId);
  }
}
