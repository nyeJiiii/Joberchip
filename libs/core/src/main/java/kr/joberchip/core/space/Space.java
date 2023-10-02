package kr.joberchip.core.space;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.BaseEntity;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.core.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "space_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Space extends BaseEntity {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "space_id", columnDefinition = "BINARY(16)")
  protected UUID spaceId;

  @OneToOne
  @JoinColumn(name = "creator_id")
  private User creator;

  @OneToOne
  @JoinColumn(name = "page_id")
  private SharePage mainPage;

  public void setMainPage(SharePage sharePage) {
    sharePage.setParentObjectId(this.spaceId);
    this.mainPage = sharePage;
  }

  public static Space create(User creator) {
    return new Space(null, creator, null);
  }

  @Override
  public String toString() {
    return "{ "
        + "spaceId : "
        + spaceId
        + ", creator : "
        + creator.getUsername()
        + ", mainPage : "
        + (this.mainPage != null ? mainPage.getPageId() : null)
        + " }";
  }
}
