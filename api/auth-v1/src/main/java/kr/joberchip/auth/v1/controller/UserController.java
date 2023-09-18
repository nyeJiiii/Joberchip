package kr.joberchip.auth.v1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/")
    public String home() {
        return "홈페이지";
    }

    @GetMapping("/user")
    public String user() {
        return "유저페이지";
    }

    @GetMapping("/login")
    public String login() {
        return "로그인페이지";
    }
}
