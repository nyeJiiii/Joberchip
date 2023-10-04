package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import javax.persistence.EntityExistsException;
import kr.joberchip.core.block.TextBlock;
import kr.joberchip.core.page.SharePage;
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
    TextBlock newTextBlock = textBlockDTO.toEntity();

    textBlockRepository.save(newTextBlock);

    SharePage parentPage =
            sharePageRepository.findById(pageId).orElseThrow(EntityExistsException::new);

    parentPage.addTextBlock(newTextBlock);

    return BlockResponseDTO.fromEntity(newTextBlock);
  }

  @Transactional
  public BlockResponseDTO modifyTextBlock(UUID pageId, UUID blockId, TextBlockDTO textBlockDTO) {
    TextBlock textBlock =
        textBlockRepository.findById(blockId).orElseThrow(EntityExistsException::new);

    if (textBlockDTO.content() != null) textBlock.setContent(textBlock.getContent());

    textBlockRepository.save(textBlock);

    return BlockResponseDTO.fromEntity(textBlock);
  }

  @Transactional
  public void deleteTextBlock(UUID pageId, UUID blockId) {
    textBlockRepository.deleteById(blockId);
  }
}
