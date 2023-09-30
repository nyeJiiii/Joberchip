package kr.joberchip.server.v1.user.repository;

import java.util.Optional;
import kr.joberchip.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Modifying
  @Query(value = "UPDATE User SET nickname = :newNickname WHERE userId = :userId")
  void updateNicknameById(@Param("newNickname") String newNickname, @Param("userId") Long userId);
}