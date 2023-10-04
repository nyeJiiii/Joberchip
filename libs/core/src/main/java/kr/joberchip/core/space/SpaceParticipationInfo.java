package kr.joberchip.core.space;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.space.types.ParticipationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "space_participation_info_tb",
    indexes = {@Index(name = "idx_info", columnList = "user_id, space_id, participation_type")})
@IdClass(SpaceParticipationInfo.class)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SpaceParticipationInfo implements Serializable {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Id
  @Column(name = "space_id", columnDefinition = "BINARY(16)")
  private UUID spaceId;

  @Enumerated(EnumType.STRING)
  @Column(name = "participation_type")
  private ParticipationType participationType;

  public static SpaceParticipationInfo of(Long userId, UUID spaceId) {
    return new SpaceParticipationInfo(userId, spaceId, ParticipationType.DEFAULT);
  }

  public static SpaceParticipationInfo of(
      Long userId, UUID spaceId, ParticipationType participationType) {
    return new SpaceParticipationInfo(userId, spaceId, participationType);
  }

  @Override
  public String toString() {
    return "{ "
        + "userId : "
        + userId
        + ", spaceId : "
        + spaceId
        + ", participationType : "
        + participationType
        + " }";
  }
}
