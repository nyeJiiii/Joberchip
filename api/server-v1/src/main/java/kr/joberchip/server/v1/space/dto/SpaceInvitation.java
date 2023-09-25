package kr.joberchip.server.v1.space.dto;

import kr.joberchip.core.user.SpaceUserInfo;

import java.util.UUID;

public record SpaceInvitation(Long userId, UUID spaceId) {
}
