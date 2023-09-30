package kr.joberchip.auth.v1.user.controller.dto;

import kr.joberchip.auth.v1.errors.ErrorMessage;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserNickname {

    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
    private String nickname;
}