package kr.joberchip.server.v1.user.controller;

import java.util.HashMap;
import java.util.Map;
import kr.joberchip.server.v1._config.security.JwtProvider;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
  private final UserRepository userRepository;

  @GetMapping("/testTokens")
  public ApiResponse.Result<Map<String, String>> generateTestTokens() {
    Map<String, String> usernameAndToken = new HashMap<>();

    userRepository
        .findAll()
        .forEach(user -> usernameAndToken.put(user.getUsername(), JwtProvider.create(user)));

    return ApiResponse.success(usernameAndToken);
  }
}
