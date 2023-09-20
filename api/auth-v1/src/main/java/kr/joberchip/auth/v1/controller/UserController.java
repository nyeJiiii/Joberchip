package kr.joberchip.auth.v1.controller;

import kr.joberchip.auth.v1._utils.ApiResponse;
import kr.joberchip.auth.v1.dto.UserRequest;
import kr.joberchip.auth.v1.service.UserService;
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
        return ResponseEntity.ok().body(ApiResponse.success("회원가입 성공"));
    }
}
