package kr.joberchip.server.v1.user.controller;

import java.util.ArrayList;
import java.util.List;
import kr.joberchip.server.v1._config.security.JwtProvider;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserRepository userRepository;

  @GetMapping("/testTokens")
  public ResponseEntity<ApiResponse.Result<List<TokenResponse>>> generateTestTokens() {
    List<TokenResponse> tokens = new ArrayList<>();

    userRepository.findAll().stream()
        .map(user -> TokenResponse.of(user.getUsername(), JwtProvider.create(user)))
        .forEach(tokens::add);

    return ResponseEntity.ok(ApiResponse.success(tokens));
  }

  public record TokenResponse(String username, String token) {
    public static TokenResponse of(String username, String token) {
      return new TokenResponse(username, token);
    }
  }
}
