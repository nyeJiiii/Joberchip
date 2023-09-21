package kr.joberchip.server.v1.space;

import java.util.UUID;
import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/space")
@RequiredArgsConstructor
public class JoberSpaceController {
  private final JoberSpaceService joberSpaceService;

  @PostMapping("/new")
  public ResponseEntity<ApiResponse.Result<UUID>> createNewSpace(
      @AuthenticationPrincipal CustomUserDetails authentication) {
    User user = authentication.getUser();

    UUID generatedSpaceId = joberSpaceService.createSpace(user.getUserId());

    return ResponseEntity.ok(ApiResponse.success(generatedSpaceId));
  }
}
