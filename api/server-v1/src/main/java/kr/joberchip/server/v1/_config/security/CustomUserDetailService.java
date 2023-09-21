package kr.joberchip.server.v1._config.security;

import java.util.Optional;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("스프링 시큐리티 로그인 서비스 호출 : {}", username);

    Optional<User> userOptional = userRepository.findByUsername(username);
    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저"));

    return new CustomUserDetails(user);
  }
}
