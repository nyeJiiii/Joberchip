package kr.joberchip.server.v1.space.controller;

import java.util.List;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.page.service.SharePageService;
import kr.joberchip.server.v1.space.dto.ParticipatingInfo;
import kr.joberchip.server.v1.space.dto.SpaceInfo;
import kr.joberchip.server.v1.space.dto.SpaceInvitation;
import kr.joberchip.server.v1.space.service.SpaceService;
import kr.joberchip.server.v1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/space")
@RequiredArgsConstructor
public class SpaceController {
  private final SpaceService spaceService;
  private final SharePageService sharePageService;
  private final UserService userService;

  @PostMapping("/new")
  public ResponseEntity<ApiResponse.Result<SpaceInfo>> createNewSpace(
      @AuthenticationPrincipal CustomUserDetails authentication) {
    Long userId = authentication.user().getUserId();

    spaceService.create(userId);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @GetMapping("/list")
  public ResponseEntity<ApiResponse.Result<List<ParticipatingInfo>>> participatingSpaces(
      @AuthenticationPrincipal CustomUserDetails authentication) {

    Long userId = authentication.user().getUserId();
    List<ParticipatingInfo> result = userService.getParticipatingSpace(userId);

    return ResponseEntity.ok(ApiResponse.success(result));
  }

  @PostMapping("/invite")
  public ResponseEntity<ApiResponse.Result<List<ParticipatingInfo>>> invite(
          @AuthenticationPrincipal CustomUserDetails authentication, SpaceInvitation spaceInvitation) {

    Long userId = authentication.user().getUserId();
    spaceService.invite(spaceInvitation);
    List<ParticipatingInfo> result = userService.getParticipatingSpace(userId);

    return ResponseEntity.ok(ApiResponse.success(result));
  }
}
