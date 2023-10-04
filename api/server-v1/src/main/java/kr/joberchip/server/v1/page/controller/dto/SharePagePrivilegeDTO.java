package kr.joberchip.server.v1.page.controller.dto;

import java.util.UUID;
import javax.validation.constraints.NotBlank;

import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.page.types.PrivilegeType;

public record SharePagePrivilegeDTO(
    @NotBlank Long userId, @NotBlank UUID sharePageId, @NotBlank PrivilegeType privilegeType) {
  public SharePagePrivilege toEntity() {
    return SharePagePrivilege.of(userId, sharePageId, privilegeType);
  }
}
