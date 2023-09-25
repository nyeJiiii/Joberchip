package kr.joberchip.auth.v1.user.dto;

import kr.joberchip.auth.v1.errors.ErrorMessage;
import kr.joberchip.core.user.User;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserRequest {

    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
    private String username;
    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
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
