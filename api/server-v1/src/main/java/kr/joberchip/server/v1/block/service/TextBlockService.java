package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import kr.joberchip.core.block.TextBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.TextBlockDTO;
import kr.joberchip.server.v1.block.repository.TextBlockRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextBlockService {
  private final SharePageRepository sharePageRepository;
  private final TextBlockRepository textBlockRepository;

  @Transactional
  public BlockResponseDTO createTextBlock(UUID pageId, TextBlockDTO textBlockDTO) {

    SharePage parentPage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    TextBlock newTextBlock = textBlockDTO.toEntity();

    textBlockRepository.save(newTextBlock);

    parentPage.addTextBlock(newTextBlock);

    log.info(
        "[TextBlockService] Text Block Created : blockId - {}, content - {}",
        newTextBlock.getObjectId(),
        newTextBlock.getContent());

    return BlockResponseDTO.fromEntity(newTextBlock);
  }

  @Transactional
  public BlockResponseDTO modifyTextBlock(UUID blockId, TextBlockDTO textBlockDTO) {
    TextBlock textBlock =
        textBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    if (textBlockDTO.content() != null) textBlock.setContent(textBlockDTO.content());
    if (textBlockDTO.visible() != null) textBlock.setVisible(textBlockDTO.visible());

    textBlockRepository.save(textBlock);

    log.info(
        "[TextBlockService] Text Block Updated : content - {}, visible - {}",
        textBlock.getContent(),
        textBlock.getVisible());

    return BlockResponseDTO.fromEntity(textBlock);
  }

  @Transactional
  public void deleteTextBlock(UUID pageId, UUID blockId) {
    SharePage parentPage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    TextBlock textBlock =
        textBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    parentPage.getTextBlocks().remove(textBlock);
    textBlockRepository.delete(textBlock);

    log.info("[TextBlockService] Delete Text block Id - {}", blockId);
  }
}
