package kr.joberchip.server.v1.share.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;

import kr.joberchip.core.share.block.TextBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1.share.block.controller.dto.TextBlockDTO;
import kr.joberchip.server.v1.share.block.repository.TextBlockRepository;
import kr.joberchip.server.v1.share.page.repository.SharePageRepository;
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
  public void createTextBlock(UUID pageId, TextBlockDTO.Create newTextBlock) {
    SharePage parentPage = isPage(pageId);
    TextBlock savedTextBlock =
            textBlockRepository.save(
                    newTextBlock.setEntity(new TextBlock())
            );
    parentPage.addTextBlock(savedTextBlock);
    log.info("UUID of NEW MAP BLOCK: " + savedTextBlock.getTextBlockId());
  }

  private SharePage isPage(UUID pageId) {
    return sharePageRepository.findById(pageId).orElseThrow(() -> {
      log.error("존재하지 않는 pageID - pageId: {}", pageId);
      throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
    });
  }

  public void modifyTextBlock(UUID pageId, UUID blockId, TextBlockDTO textBlockDTO) {
  }

  public void deleteTextBlock(UUID pageId, UUID blockId) {
  }
}
