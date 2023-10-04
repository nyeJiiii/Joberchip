package kr.joberchip.server.v1.page.service;

import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.page.SharePagePrivilege;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.page.controller.dto.SharePagePrivilegeDTO;
import kr.joberchip.server.v1.page.repository.SharePagePrivilegeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SharePagePrivilegeService {
  private final SharePagePrivilegeRepository sharePagePrivilegeRepository;

  @Transactional
  public void registerPrivilege(Long userId, UUID sharePageId, PrivilegeType privilegeType) {
    SharePagePrivilege sharePagePrivilege =
        SharePagePrivilege.of(userId, sharePageId, privilegeType);
    sharePagePrivilegeRepository.save(sharePagePrivilege);
  }

  @Transactional
  public void modifyPrivilege(Long userId, UUID sharePageId, PrivilegeType privilegeType) {

    SharePagePrivilege sharePagePrivilege =
        sharePagePrivilegeRepository
            .findByUserIdAndSharePageId(userId, sharePageId)
            .orElseThrow(EntityNotFoundException::new);

    sharePagePrivilege.setPrivilegeType(privilegeType);
  }

  @Transactional
  public void modifyPrivilege(Long userId, SharePagePrivilegeDTO sharePagePrivilegeDTO) {
    PrivilegeType requestUserPrivilegeType =
        sharePagePrivilegeRepository
            .findByUserIdAndSharePageId(userId, sharePagePrivilegeDTO.sharePageId())
            .orElseThrow(EntityNotFoundException::new)
            .getPrivilegeType();

    if (requestUserPrivilegeType == PrivilegeType.READ) {
      throw new ApiClientException(ErrorMessage.FORBIDDEN);
    }

    Optional<SharePagePrivilege> optionalSharePagePrivilege =
        sharePagePrivilegeRepository.findByUserIdAndSharePageId(
            sharePagePrivilegeDTO.userId(), sharePagePrivilegeDTO.sharePageId());

    if (optionalSharePagePrivilege.isEmpty()) {
      sharePagePrivilegeRepository.save(sharePagePrivilegeDTO.toEntity());
      return;
    }

    optionalSharePagePrivilege.get().setPrivilegeType(sharePagePrivilegeDTO.privilegeType());
  }
}
