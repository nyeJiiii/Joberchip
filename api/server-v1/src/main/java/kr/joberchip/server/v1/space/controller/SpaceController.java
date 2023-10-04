package kr.joberchip.server.v1.space.controller;

import java.util.List;
import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.page.controller.dto.SharePageTreeResponseDTO;
import kr.joberchip.server.v1.page.service.SharePageService;
import kr.joberchip.server.v1.space.controller.dto.ParticipationInfoResponseDTO;
import kr.joberchip.server.v1.space.controller.dto.SpaceInviteRequestDTO;
import kr.joberchip.server.v1.space.service.SpaceParticipationInfoService;
import kr.joberchip.server.v1.space.service.SpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/space")
@RequiredArgsConstructor
public class SpaceController {
  private final SpaceService spaceService;
  private final SpaceParticipationInfoService spaceParticipationInfoService;
  private final SharePageService sharePageService;

  @PostMapping("/new")
  public ResponseEntity<ApiResponse.Result<Object>> createSpace(
      @AuthenticationPrincipal CustomUserDetails loginUser) {

    UUID defaultPageId = sharePageService.createDefaultPage(loginUser.user().getUserId());
    UUID generatedSpaceId = spaceService.createSpace(loginUser.user().getUserId(), defaultPageId);

    spaceParticipationInfoService.registerOwnerInfo(loginUser.user().getUserId(), generatedSpaceId);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @GetMapping("/list")
  public ResponseEntity<ApiResponse.Result<List<ParticipationInfoResponseDTO>>> participatingSpaces(
      @AuthenticationPrincipal CustomUserDetails authentication) {

    Long userId = authentication.user().getUserId();

    List<ParticipationInfoResponseDTO> result = spaceService.getParticipatingInfo(userId);

    return ResponseEntity.ok(ApiResponse.success(result));
  }

  @DeleteMapping("/{spaceId}")
  public ResponseEntity<ApiResponse.Result<Object>> deleteSpace(
      @AuthenticationPrincipal CustomUserDetails loginUser, @PathVariable UUID spaceId) {

    spaceService.deleteSpace(loginUser.user().getUserId(), spaceId);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @PostMapping("/invite")
  public ResponseEntity<ApiResponse.Result<Object>> invite(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @RequestBody SpaceInviteRequestDTO spaceInviteRequestDTO) {

    spaceParticipationInfoService.registerInvitation(
        loginUser.user().getUserId(), spaceInviteRequestDTO);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @GetMapping("/tree")
  public ResponseEntity<ApiResponse.Result<SharePageTreeResponseDTO.PageTreeNode>> getPageTree(
      @RequestParam UUID spaceId) {
    SharePageTreeResponseDTO.PageTreeNode response = spaceService.spaceSharePageTree(spaceId);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
