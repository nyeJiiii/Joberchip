package kr.joberchip.core.space;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.BaseEntity;
import kr.joberchip.core.space.page.SpacePage;
import kr.joberchip.core.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "jober_space_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JoberSpace extends BaseEntity {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "space_id", columnDefinition = "BINARY(16)")
  protected UUID spaceId;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;

  @OneToOne
  @JoinColumn(name = "main_page_id")
  private SpacePage mainPage;

  public static JoberSpace of(User owner, SpacePage mainPage) {
    return new JoberSpace(null, owner, mainPage);
  }
}
