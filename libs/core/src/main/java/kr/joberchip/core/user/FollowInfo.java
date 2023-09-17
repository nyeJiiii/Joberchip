package kr.joberchip.core.user;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow_info_tb")
@IdClass(FollowInfo.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FollowInfo implements Serializable {
  @Id
  @Column(name = "following", insertable = false, updatable = false)
  private Long followingUserId;

  @Id
  @Column(name = "follower", insertable = false, updatable = false)
  private Long followerUserId;

  public static FollowInfo of(Long followingUserId, Long followerUserId) {
    return new FollowInfo(followingUserId, followerUserId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FollowInfo that = (FollowInfo) o;
    return Objects.equals(followingUserId, that.followingUserId)
        && Objects.equals(followerUserId, that.followerUserId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(followingUserId, followerUserId);
  }
}
