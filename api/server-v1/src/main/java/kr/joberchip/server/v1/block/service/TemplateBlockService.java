package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import kr.joberchip.core.block.TemplateBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.TemplateBlockDTO;
import kr.joberchip.server.v1.block.repository.TemplateBlockRepository;
import kr.joberchip.server.v1.page.repository.SharePagePrivilegeRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateBlockService {
  private final SharePagePrivilegeRepository sharePagePrivilegeRepository;
  private final SharePageRepository sharePageRepository;
  private final TemplateBlockRepository templateBlockRepository;

  public BlockResponseDTO createTemplateBlock(
      Long userId, UUID pageId, TemplateBlockDTO templateBlockDTO) {

    PrivilegeType privilegeType =
        sharePagePrivilegeRepository
            .findByUserIdAndSharePageId(userId, pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.FORBIDDEN))
            .getPrivilegeType();

    SharePage sharePage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.ENTITY_NOT_FOUND));

    TemplateBlock newTemplateBlock = templateBlockDTO.toEntity();
    templateBlockRepository.save(newTemplateBlock);
    sharePage.addTemplateBlock(newTemplateBlock);

    return BlockResponseDTO.fromEntity(newTemplateBlock);
  }

  public BlockResponseDTO modifyTemplateBlock(
      Long userId, UUID pageId, UUID blockId, TemplateBlockDTO templateBlockDTO) {

    TemplateBlock templateBlock =
        templateBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));



    return null;
  }

  public void deleteTemplateBlock(UUID pageId, UUID blockId) {
    SharePage sharePage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    log.info("[TemplateBlockService] Current Page : {}", sharePage);

    TemplateBlock templateBlock =
        templateBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    log.info("[TemplateBlockService] Remove Template Block : {}", templateBlock);

    sharePage.getTemplateBlocks().remove(templateBlock);
    templateBlockRepository.delete(templateBlock);

    log.info("[TemplateBlockService] Remove - success");
  }
}
