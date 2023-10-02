package kr.joberchip.core.user;

import java.util.List;
import javax.persistence.*;

import kr.joberchip.core.storage.ProfileImageFile;
import kr.joberchip.core.storage.UserProfileImageFile;
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

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserProfileImageFile userProfileImageFile;

  @Builder.Default private String userRoles = "ROLE_USER";

  @OneToMany(mappedBy = "user")
  private List<SpaceUserInfo> spaceUserInfoList;

  @PrePersist
  protected void onCreate() {
    nickname = username;
  }

}
