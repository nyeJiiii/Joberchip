package kr.joberchip.server.v1.user.controller;

import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.user.dto.UserNickname;
import kr.joberchip.server.v1.user.dto.UserRequest;
import kr.joberchip.server.v1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest newUser, Errors errors) {
        userService.join(newUser);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest loginUser, Errors error) {
        String token = userService.login(loginUser);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body(ApiResponse.success());
    }

    @PutMapping("/user/nickname")
    public ApiResponse.Result<Object> modifyNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UserNickname userNickname,
            Errors errors) {
        userService.modifyNickname(userDetails, userNickname);
        return ApiResponse.success();
    }
}
