package kr.joberchip.server.v1.user.service;

import java.util.List;
import kr.joberchip.server.v1.space.dto.ParticipatingInfo;
import kr.joberchip.server.v1.user.repository.SpaceUserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final SpaceUserInfoRepository spaceUserInfoRepository;

  public List<ParticipatingInfo> getParticipatingSpace(Long userId) {
    return spaceUserInfoRepository.findAllByUser_UserId(userId).stream()
        .map(ParticipatingInfo::fromEntity)
        .toList();
  }
}
