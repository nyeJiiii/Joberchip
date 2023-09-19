package kr.joberchip.auth.v1.repository;

import java.util.Optional;
import kr.joberchip.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(@Param("username") String username);
}
