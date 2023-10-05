package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.block.LinkBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.LinkBlockDTO;
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
  public BlockResponseDTO createLinkBlock(UUID pageId, LinkBlockDTO createLinkBlock) {
    LinkBlock newLinkBlock = createLinkBlock.toEntity();
    linkBlockRepository.save(newLinkBlock);

    SharePage parentPage =
        sharePageRepository.findById(pageId).orElseThrow(EntityNotFoundException::new);

    parentPage.addLinkBlock(newLinkBlock);

    return BlockResponseDTO.fromEntity(newLinkBlock);
  }

  @Transactional
  public BlockResponseDTO modifyLinkBlock(UUID blockId, LinkBlockDTO modifyRequestDTO) {

    LinkBlock linkBlock =
        linkBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    if (modifyRequestDTO.title() != null) linkBlock.setTitle(modifyRequestDTO.title());
    if (modifyRequestDTO.description() != null)
      linkBlock.setDescription(modifyRequestDTO.description());
    if (modifyRequestDTO.link() != null) linkBlock.setLink(modifyRequestDTO.link());
    if (modifyRequestDTO.visible() != null) linkBlock.setVisible(modifyRequestDTO.visible());

    linkBlockRepository.save(linkBlock);

    return BlockResponseDTO.fromEntity(linkBlock);
  }

  @Transactional
  public void deleteLinkBlock(UUID pageId, UUID blockId) {
    SharePage parentPage =
        sharePageRepository
            .findSharePageByObjectId(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    LinkBlock block =
        linkBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    parentPage.getLinkBlocks().remove(block);

    linkBlockRepository.delete(block);
  }
}
