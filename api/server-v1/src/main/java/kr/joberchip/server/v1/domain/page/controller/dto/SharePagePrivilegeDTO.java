package kr.joberchip.server.v1.domain.page.controller.dto;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.page.types.PrivilegeType;

public record SharePagePrivilegeDTO(
    @NotBlank Long userId, @NotBlank UUID sharePageId, @NotBlank PrivilegeType privilegeType) {
  public SharePagePrivilege toEntity() {
    return SharePagePrivilege.of(userId, sharePageId, privilegeType);
  }

  public static SharePagePrivilegeDTO fromEntity(SharePagePrivilege sharePagePrivilege) {
    return new SharePagePrivilegeDTO(
        sharePagePrivilege.getUserId(),
        sharePagePrivilege.getSharePageId(),
        sharePagePrivilege.getPrivilegeType());
  }
}
