package kr.joberchip.server.v1.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.joberchip.core.user.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserResponseDTO(String nickname, String profileImageLink) {

    public static UpdateUserResponseDTO fromEntity(User user) {
        return new UpdateUserResponseDTO(
                user.getNickname(),
                user.getProfileImageLink());
    }
}
