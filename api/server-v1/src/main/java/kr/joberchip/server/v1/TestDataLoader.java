package kr.joberchip.server.v1;

import java.util.List;
import java.util.UUID;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1.space.JoberSpaceRepository;
import kr.joberchip.server.v1.space.page.repository.SpacePageRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {
  private final SpacePageRepository spacePageRepository;
  private final JoberSpaceRepository joberSpaceRepository;
  private final UserRepository userRepository;

  @Override
  public void run(final String... args) throws Exception {
    UUID parentId = UUID.fromString("00000000-0000-0000-0000-000000000000");
    createMockUserAndSpace();
  }

  private void createMockUserAndSpace() {
    User mock1 = User.builder().username("mock1").password("{noop}mockPassword1").build();
    User mock2 = User.builder().username("mock2").password("{noop}mockPassword2").build();
    User mock3 = User.builder().username("mock3").password("{noop}mockPassword3").build();
    User mock4 = User.builder().username("mock4").password("{noop}mockPassword4").build();
    User mock5 = User.builder().username("mock5").password("{noop}mockPassword5").build();

    List<User> mockUsers = List.of(mock1, mock2, mock3, mock4, mock5);
    userRepository.saveAll(mockUsers);
    //
    //    SpacePage page1 = SpacePage.of("mock1 user page", "사용자1 공유 페이지 메인");
    //    SpacePage page2 = SpacePage.of("mock2 user page", "사용자2 공유 페이지 메인");
    //    SpacePage page3 = SpacePage.of("mock3 user page", "사용자3 공유 페이지 메인");
    //    SpacePage page4 = SpacePage.of("mock4 user page", "사용자4 공유 페이지 메인");
    //    SpacePage page5 = SpacePage.of("mock5 user page", "사용자5 공유 페이지 메인");
    //
    //    List<SpacePage> mockPages = List.of(page1, page2, page3, page4, page5);
    //    mockPages.forEach(
    //        page -> {
    //          page.setParentObjectId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
    //          page.setLocation(0, 0, 0, 0);
    //        });
    //    spacePageRepository.saveAll(mockPages);
  }
}
