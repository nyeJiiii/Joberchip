package kr.joberchip.server.v1;

import java.util.List;
import java.util.stream.IntStream;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestUserLoader implements CommandLineRunner {

  private final UserRepository userRepository;

  @Override
  public void run(final String... args) throws Exception {
    createMockUser();
  }

  private void createMockUser() {

    List<User> mockUsers =
        IntStream.range(1, 6)
            .mapToObj(
                num ->
                    User.builder()
                        .username("mock" + num)
                        .password("{noop}mockPassword" + num)
                        .build())
            .toList();

    userRepository.saveAll(mockUsers);
  }
}
