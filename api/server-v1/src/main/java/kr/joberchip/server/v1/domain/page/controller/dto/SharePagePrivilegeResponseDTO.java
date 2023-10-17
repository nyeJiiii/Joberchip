package kr.joberchip.server.v1.domain.page.controller.dto;

import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.page.types.PrivilegeType;

public record SharePagePrivilegeResponseDTO(PrivilegeType privilegeType) {
  public static SharePagePrivilegeResponseDTO fromEntity(SharePagePrivilege sharePagePrivilege) {
    return new SharePagePrivilegeResponseDTO(sharePagePrivilege.getPrivilegeType());
  }
}
