package kr.joberchip.auth.v1.user.controller;

import kr.joberchip.auth.v1._utils.ApiResponse;
import kr.joberchip.auth.v1.user.dto.UserRequest;
import kr.joberchip.auth.v1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "홈페이지";
    }

    @GetMapping("/user")
    public String user() {
        return "유저페이지";
    }

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
}
