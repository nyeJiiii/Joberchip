package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.block.LinkBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.LinkBlockRequestDTO;
import kr.joberchip.server.v1.block.repository.LinkBlockRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
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
  public BlockResponseDTO createLinkBlock(UUID pageId, LinkBlockRequestDTO createLinkBlock) {
    LinkBlock newLinkBlock = createLinkBlock.toEntity();
    linkBlockRepository.save(newLinkBlock);

    SharePage parentPage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    parentPage.addLinkBlock(newLinkBlock);

    return BlockResponseDTO.fromEntity(newLinkBlock);
  }

  @Transactional
  public BlockResponseDTO modifyLinkBlock(UUID pageId, UUID blockId, LinkBlockRequestDTO modifyRequestDTO) {

    LinkBlock target =
        linkBlockRepository.findById(blockId).orElseThrow(EntityNotFoundException::new);

    if (modifyRequestDTO.title() != null) target.modifyTitle(modifyRequestDTO.title());

    if (modifyRequestDTO.description() != null)
      target.modifyDescription(modifyRequestDTO.description());

    if (modifyRequestDTO.link() != null) target.modifyLink(modifyRequestDTO.link());

    linkBlockRepository.save(target);

    return BlockResponseDTO.fromEntity(target);
  }

  public void deleteLinkBlock(UUID pageId, UUID blockId) {
    LinkBlock block =
        linkBlockRepository.findById(blockId).orElseThrow(EntityNotFoundException::new);

    SharePage parent =
        sharePageRepository
            .findSharePageByObjectId(pageId)
            .orElseThrow(EntityNotFoundException::new);

    parent.getLinkBlocks().remove(block);

    linkBlockRepository.delete(block);
  }
}
