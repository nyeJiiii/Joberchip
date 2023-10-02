package kr.joberchip.core.page;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.page.types.PrivilegeType;
import lombok.*;

@Entity
@Table(
    name = "share_page_privilege_tb",
    indexes = {@Index(name = "idx_user_page", columnList = "user_id, share_page_id")})
@IdClass(SharePagePrivilege.class)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SharePagePrivilege implements Serializable {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Id
  @Column(name = "share_page_id", columnDefinition = "BINARY(16)")
  private UUID sharePageId;

  @Enumerated(EnumType.STRING)
  @Setter
  private PrivilegeType privilegeType;

  public void modifyPrivilege(PrivilegeType privilegeType) {
    this.privilegeType = privilegeType;
  }

  public static SharePagePrivilege of(Long userId, UUID pageId, PrivilegeType privilegeType) {
    return new SharePagePrivilege(userId, pageId, privilegeType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SharePagePrivilege that = (SharePagePrivilege) o;
    return Objects.equals(userId, that.userId) && Objects.equals(sharePageId, that.sharePageId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, sharePageId);
  }
}
