package kr.joberchip.server.v1.space.page.service;

import kr.joberchip.server.v1.space.page.dto.SpacePageResponse;
import kr.joberchip.server.v1.space.page.repository.SpacePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpacePageService {

  private final SpacePageRepository spacePageRepository;

  public SpacePageResponse getSpacePage() {
    return new SpacePageResponse();
  }
}
