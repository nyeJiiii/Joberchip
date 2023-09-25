package kr.joberchip.server.v1.space.service;

import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.core.space.ParticipationType;
import kr.joberchip.core.space.Space;
import kr.joberchip.core.user.SpaceUserInfo;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1.share.page.repository.SharePageRepository;
import kr.joberchip.server.v1.space.dto.SpaceInfo;
import kr.joberchip.server.v1.space.dto.SpaceInvitation;
import kr.joberchip.server.v1.space.repository.SpaceRepository;
import kr.joberchip.server.v1.user.repository.SpaceUserInfoRepository;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpaceService {
  private static final String defaultPageTitle = "의 스페이스";
  private static final String defaultPageDescription = "의 스페이스입니다.";

  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;
  private final SharePageRepository sharePageRepository;
  private final SpaceUserInfoRepository spaceUserInfoRepository;

  @Transactional
  public void create(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

    Space defaultSpace = Space.createDefault(user);

    spaceRepository.save(defaultSpace);

    SharePage defaultSharePage = generateDefaultSharePage(defaultSpace, user.getUsername());

    defaultSpace.setMainPage(defaultSharePage);

    if (spaceUserInfoRepository.existsByUser_UserIdAndParticipationType(
        userId, ParticipationType.DEFAULT)) {
      SpaceUserInfo memberInfo = SpaceUserInfo.of(defaultSpace, user, ParticipationType.OWNER);
      spaceUserInfoRepository.save(memberInfo);
    } else {
      SpaceUserInfo memberInfo = SpaceUserInfo.of(defaultSpace, user);
      spaceUserInfoRepository.save(memberInfo);
    }
  }

  private SharePage generateDefaultSharePage(Space defaultSpace, String username) {
    SharePage defaultSharePage =
        SharePage.of(
            defaultSpace.getSpaceId(),
            username + defaultPageTitle,
            username + defaultPageDescription);

    sharePageRepository.save(defaultSharePage);

    return defaultSharePage;
  }

  @Transactional(readOnly = true)
  public SpaceInfo getDefaultSpace(Long userId) {
    Space defaultSpace =
        spaceRepository.findByCreator_UserId(userId).orElseThrow(EntityNotFoundException::new);
    return SpaceInfo.fromEntity(defaultSpace);
  }

  @Transactional
  public void invite(SpaceInvitation spaceInvitation) {
    User user =
        userRepository.findById(spaceInvitation.userId()).orElseThrow(EntityNotFoundException::new);

    Space space =
        spaceRepository
            .findById(spaceInvitation.spaceId())
            .orElseThrow(EntityNotFoundException::new);

    SpaceUserInfo.of(space, user, ParticipationType.PARTICIPANT);
  }
}
