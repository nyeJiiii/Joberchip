package kr.joberchip.auth.v1.user.dto;

import kr.joberchip.core.user.User;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserRequest {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public void encodePassword (String password) {
        this.password = password;
    }

    public User toEntity () {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }

}
