package kr.joberchip.server.v1.space;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.space.JoberSpace;
import kr.joberchip.core.space.page.SpacePage;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1.space.page.repository.SpacePageRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoberSpaceService {
  private static final String defaultPageTitle = "의 스페이스";
  private static final String defaultPageDescription = "의 스페이스입니다.";

  private final UserRepository userRepository;
  private final JoberSpaceRepository joberSpaceRepository;
  private final SpacePageRepository spacePageRepository;

  /**
   * @param userId 스페이스 생성 사용자 userId;
   * @return 생성된 스페이스 UUID
   */
  @Transactional
  public UUID createSpace(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

    SpacePage defaultPage =
        SpacePage.of(
            user.getUsername() + defaultPageTitle, user.getUsername() + defaultPageDescription);

    spacePageRepository.save(defaultPage);

    JoberSpace generatedSpace = joberSpaceRepository.save(JoberSpace.of(user, defaultPage));
    return generatedSpace.getSpaceId();
  }
}
