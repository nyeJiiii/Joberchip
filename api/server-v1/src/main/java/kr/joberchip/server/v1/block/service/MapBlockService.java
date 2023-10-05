package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import kr.joberchip.core.block.MapBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.MapBlockDTO;
import kr.joberchip.server.v1.block.repository.MapBlockRepository;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapBlockService {

  private final MapBlockRepository mapBlockRepository;
  private final SharePageRepository sharePageRepository;

  @Transactional
  public BlockResponseDTO createMapBlock(UUID pageId, MapBlockDTO crateMapBlockDTO) {

    SharePage parentPage =
        sharePageRepository
            .findSharePageByObjectId(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    MapBlock newMapBlock = crateMapBlockDTO.toEntity();

    mapBlockRepository.save(newMapBlock);
    parentPage.addMapBlock(newMapBlock);

    log.info("UUID of NEW MAP BLOCK: " + newMapBlock.getObjectId());

    return BlockResponseDTO.fromEntity(newMapBlock);
  }

  @Transactional
  public BlockResponseDTO modifyMapBlock(UUID blockId, MapBlockDTO modifyMapBlockDTO) {
    MapBlock mapBlock =
        mapBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    if (modifyMapBlockDTO.address() != null) mapBlock.setAddress(modifyMapBlockDTO.address());
    if (modifyMapBlockDTO.latitude() != null) mapBlock.setLatitude(modifyMapBlockDTO.latitude());
    if (modifyMapBlockDTO.longitude() != null) mapBlock.setLongitude(modifyMapBlockDTO.longitude());
    if (modifyMapBlockDTO.visible() != null) mapBlock.setVisible(modifyMapBlockDTO.visible());

    mapBlockRepository.save(mapBlock);

    return BlockResponseDTO.fromEntity(mapBlock);
  }

  @Transactional
  public void deleteMapBlock(UUID pageId, UUID blockId) {
    SharePage parentPage =
        sharePageRepository
            .findById(pageId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.SHARE_PAGE_ENTITY_NOT_FOUND));

    MapBlock mapBlock =
        mapBlockRepository
            .findById(blockId)
            .orElseThrow(() -> new ApiClientException(ErrorMessage.BLOCK_ENTITY_NOT_FOUND));

    parentPage.getMapBlocks().remove(mapBlock);

    mapBlockRepository.delete(mapBlock);
  }
}
