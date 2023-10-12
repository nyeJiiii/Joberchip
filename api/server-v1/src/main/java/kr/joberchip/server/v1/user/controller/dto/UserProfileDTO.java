package kr.joberchip.server.v1.user.controller.dto;

import javax.persistence.Lob;
import kr.joberchip.core.user.User;

public record UserProfileDTO(
    Long userId, String username, String nickname, @Lob String profileImageLink) {
  public static UserProfileDTO fromEntity(User user) {
    return new UserProfileDTO(
        user.getUserId(), user.getUsername(), user.getNickname(), user.getProfileImageLink());
  }
}
