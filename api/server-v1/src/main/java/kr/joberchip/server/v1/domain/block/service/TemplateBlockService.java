package kr.joberchip.server.v1.domain.block.service;

import java.util.UUID;
import kr.joberchip.core.block.TemplateBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1.global.errors.ErrorMessage;
import kr.joberchip.server.v1.global.errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.domain.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.domain.block.controller.dto.TemplateBlockDTO;
import kr.joberchip.server.v1.domain.block.repository.TemplateBlockRepository;
import kr.joberchip.server.v1.domain.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateBlockService {
  private final SharePageRepository sharePageRepository;
  private final TemplateBlockRepository templateBlockRepository;

  @Transactional
  public BlockResponseDTO createTemplateBlock(UUID pageId, TemplateBlockDTO templateBlockDTO) {

    SharePage sharePage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    TemplateBlock newTemplateBlock = templateBlockDTO.toEntity();
    templateBlockRepository.save(newTemplateBlock);
    sharePage.addTemplateBlock(newTemplateBlock);

    return BlockResponseDTO.fromEntity(newTemplateBlock);
  }

  @Transactional
  public BlockResponseDTO modifyTemplateBlock(UUID blockId, TemplateBlockDTO templateBlockDTO) {

    TemplateBlock templateBlock =
        templateBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    if (templateBlockDTO.title() != null) templateBlock.setTitle(templateBlockDTO.title());
    if (templateBlockDTO.description() != null)
      templateBlock.setDescription(templateBlockDTO.description());
    if (templateBlockDTO.visible() != null) templateBlock.setVisible(templateBlockDTO.visible());

    templateBlockRepository.save(templateBlock);

    return BlockResponseDTO.fromEntity(templateBlock);
  }

  @Transactional
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
