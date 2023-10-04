package kr.joberchip.server.v1.user.controller.dto;

import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UserNickname {

    @NotEmpty(message = ErrorMessage.NOT_EMPTY)
    private String nickname;
}