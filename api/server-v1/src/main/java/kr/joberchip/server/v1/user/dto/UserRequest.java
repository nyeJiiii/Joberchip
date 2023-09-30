package kr.joberchip.server.v1.user.dto;

import kr.joberchip.core.user.User;
import kr.joberchip.server.v1._errors.ErrorMessage;
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
