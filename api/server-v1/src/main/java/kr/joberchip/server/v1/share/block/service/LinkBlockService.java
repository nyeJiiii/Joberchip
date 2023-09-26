package kr.joberchip.server.v1.share.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.share.block.LinkBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.server.v1.share.block.dto.create.CreateLinkBlock;
import kr.joberchip.server.v1.share.block.dto.modify.ModifyLinkBlock;
import kr.joberchip.server.v1.share.block.dto.response.LinkBlockResponse;
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
  public LinkBlockResponse createLinkBlock(UUID pageId, CreateLinkBlock createLinkBlock) {
    LinkBlock newLinkBlock = createLinkBlock.toEntity();
    linkBlockRepository.save(newLinkBlock);

    SharePage parentPage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    parentPage.addLinkBlock(newLinkBlock);

    return LinkBlockResponse.fromEntity(newLinkBlock);
  }

  public void deleteLinkBlock(UUID pageId, UUID blockId) {}

  public void modifyLinkBlock(UUID pageId, UUID blockId, ModifyLinkBlock modifyLinkBlock) {}
}
