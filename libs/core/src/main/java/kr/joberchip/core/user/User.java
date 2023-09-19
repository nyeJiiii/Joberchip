package kr.joberchip.core.user;

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
}
