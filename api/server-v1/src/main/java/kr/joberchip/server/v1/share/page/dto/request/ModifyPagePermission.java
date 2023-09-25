package kr.joberchip.server.v1.share.page.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import kr.joberchip.core.user.types.PermissionType;

public record ModifyPagePermission(
    @NotBlank Long userId, @NotBlank UUID pageId, @NotBlank PermissionType permissionType) {}
