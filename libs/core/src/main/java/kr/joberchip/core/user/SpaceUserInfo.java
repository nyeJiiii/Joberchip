package kr.joberchip.core.user;

import javax.persistence.*;
import kr.joberchip.core.space.types.ParticipationType;
import kr.joberchip.core.space.Space;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "space_user_info_tb",
    indexes = {
      @Index(name = "idx_space_participation", columnList = "space_id, participation_type")
    })
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SpaceUserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "info_id")
  private Long spaceUserInfoId;

  @ManyToOne
  @JoinColumn(name = "space_id")
  private Space space;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "participation_type")
  private ParticipationType participationType;

  public static SpaceUserInfo of(Space space, User user) {
    return new SpaceUserInfo(null, space, user, ParticipationType.DEFAULT);
  }

  public static SpaceUserInfo of(Space space, User user, ParticipationType participationType) {
    return new SpaceUserInfo(null, space, user, participationType);
  }

  @Override
  public String toString() {
    return "\n{\n"
        + "\tspaceMemberInfoId : "
        + spaceUserInfoId
        + ",\n\t space : "
        + space
        + ",\n\t user : "
        + user
        + ",\n\t participationType : "
        + participationType
        + "\n}\n";
  }
}
