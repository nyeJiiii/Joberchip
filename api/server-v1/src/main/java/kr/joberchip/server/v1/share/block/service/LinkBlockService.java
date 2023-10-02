package kr.joberchip.server.v1.share.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.share.block.LinkBlock;
import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1.share.block.controller.dto.LinkBlockDTO;
import kr.joberchip.server.v1.share.block.controller.dto.MapBlockDTO;
import kr.joberchip.server.v1.share.block.repository.LinkBlockRepository;
import kr.joberchip.server.v1.share.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkBlockService {
  private final LinkBlockRepository linkBlockRepository;
  private final SharePageRepository sharePageRepository;

  @Transactional
  public void createLinkBlock(UUID pageId, LinkBlockDTO.Create newLinkBlock) {
    SharePage parentPage = isPage(pageId);
    LinkBlock savedLinkBlock =
            linkBlockRepository.save(
                    newLinkBlock.setEntity(new LinkBlock())
            );
    parentPage.addLinkBlock(savedLinkBlock);
    log.info("UUID of NEW LINK BLOCK: " + savedLinkBlock.getLinkBlockId());
  }

  public void modifyLinkBlock(UUID blockId, LinkBlockDTO.Modify modifiedLinkBlock) {
    LinkBlock linkBlock = isBlock(blockId);
    if(modifiedLinkBlock.getTitle()!=null)
      linkBlock.modifyTitle(modifiedLinkBlock.getTitle());
    if(modifiedLinkBlock.getLink()!=null)
      linkBlock.modifyLink(modifiedLinkBlock.getLink());
  }

  @Transactional
  public LinkBlockDTO.ReturnVisible changeVisible(UUID blockId) {
    LinkBlock linkBlock = isBlock(blockId);
    linkBlock.changeVisible();
    return new LinkBlockDTO.ReturnVisible(linkBlock);
  }

  public void deleteLinkBlock(UUID pageId, UUID blockId) {}


  private SharePage isPage(UUID pageId) {
    return sharePageRepository.findById(pageId).orElseThrow(() -> {
      log.error("존재하지 않는 pageID - pageId: {}", pageId);
      throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
    });
  }

  private LinkBlock isBlock(UUID blockId) {
    return linkBlockRepository.findById(blockId).orElseThrow(() -> {
      log.error("존재하지 않는 blockId - blockId: {}", blockId);
      throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
    });
  }
}
