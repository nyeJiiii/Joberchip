package kr.joberchip.core.user;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.user.types.PermissionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "user_page_permission",
    indexes = {@Index(name = "idx_user_page", columnList = "user_id, page_id")})
@IdClass(PagePermission.class)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PagePermission implements Serializable {
  @Id
  @Column(name = "user_id")
  private Long userId;

  @Id
  @Column(name = "page_id", columnDefinition = "BINARY(16)")
  private UUID pageId;

  @Id
  @Enumerated(EnumType.STRING)
  private PermissionType permissionType;

  public void modifyPermission(PermissionType permissionType) {
    this.permissionType = permissionType;
  }

  public static PagePermission of(Long userId, UUID pageId, PermissionType permissionType) {
    return new PagePermission(userId, pageId, permissionType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PagePermission that = (PagePermission) o;
    return Objects.equals(userId, that.userId)
        && Objects.equals(pageId, that.pageId)
        && permissionType == that.permissionType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, pageId, permissionType);
  }
}
