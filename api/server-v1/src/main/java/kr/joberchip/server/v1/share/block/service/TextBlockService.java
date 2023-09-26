package kr.joberchip.server.v1.share.block.service;

import java.util.UUID;
import javax.persistence.EntityExistsException;
import kr.joberchip.core.share.block.TextBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.server.v1.share.block.controller.dto.TextBlockDTO;
import kr.joberchip.server.v1.share.block.controller.dto.TextBlockResponse;
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
  public TextBlockResponse createTextBlock(UUID pageId, TextBlockDTO textBlockDTO) {
    TextBlock newTextBlock = textBlockDTO.toEntity();

    textBlockRepository.save(newTextBlock);

    SharePage parentPage =
        sharePageRepository.findById(pageId).orElseThrow(EntityExistsException::new);

    parentPage.addTextBlock(newTextBlock);

    return TextBlockResponse.fromEntity(newTextBlock);
  }

  public void modifyTextBlock(UUID pageId, UUID blockId, TextBlockDTO textBlockDTO) {
  }

  public void deleteTextBlock(UUID pageId, UUID blockId) {
  }
}
