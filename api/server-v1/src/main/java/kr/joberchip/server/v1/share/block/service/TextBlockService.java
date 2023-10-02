package kr.joberchip.server.v1.share.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;

import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.core.share.block.TextBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1.share.block.controller.dto.MapBlockDTO;
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

  @Transactional
  public void modifyTextBlock(UUID blockId, TextBlockDTO.Modify modifiedTextBlock) {
    TextBlock textBlock = isBlock(blockId);
    if(modifiedTextBlock.getContent()!=null)
      textBlock.setContent(modifiedTextBlock.getContent());
  }

  @Transactional
  public TextBlockDTO.ReturnVisible changeVisible(UUID blockId) {
    TextBlock textBlock = isBlock(blockId);
    textBlock.changeVisible();
    return new TextBlockDTO.ReturnVisible(textBlock);
  }

  @Transactional
  public void deleteTextBlock(UUID blockId) {
    isBlock(blockId);
    textBlockRepository.deleteById(blockId);
  }

  private SharePage isPage(UUID pageId) {
    return sharePageRepository.findById(pageId).orElseThrow(() -> {
      log.error("존재하지 않는 pageID - pageId: {}", pageId);
      throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
    });
  }


  private TextBlock isBlock(UUID blockId) {
    return textBlockRepository.findById(blockId).orElseThrow(() -> {
      log.error("존재하지 않는 blockId - blockId: {}", blockId);
      throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
    });
  }

}
