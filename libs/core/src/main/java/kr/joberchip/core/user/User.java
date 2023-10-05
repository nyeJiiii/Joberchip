package kr.joberchip.core.user;

import java.util.Objects;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_tb")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Setter
  @Column(nullable = false)
  private String nickname;

  @Column(name = "profile_image_link")
  @Lob
  @Setter
  private String profileImageLink;

  @Builder.Default private String userRoles = "ROLE_USER";

  @PrePersist
  protected void onCreate() {
    nickname = username;
    profileImageLink = "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}
