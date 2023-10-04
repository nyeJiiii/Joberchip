package kr.joberchip.server.v1.block.service;

import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import kr.joberchip.core.block.MapBlock;
import kr.joberchip.core.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
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
  public BlockResponseDTO createMapBlock(UUID pageId, MapBlockDTO.Create crateMapBlockDTO) {
    SharePage parentPage =
            getParentPage(pageId);

    MapBlock newMapBlock = crateMapBlockDTO.toEntity();

    mapBlockRepository.save(newMapBlock);
    parentPage.addMapBlock(newMapBlock);

    log.info("UUID of NEW MAP BLOCK: " + newMapBlock.getObjectId());

    return BlockResponseDTO.fromEntity(newMapBlock);
  }

  private SharePage getParentPage(UUID pageId) {
    return sharePageRepository
            .findSharePageByObjectId(pageId)
            .orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public BlockResponseDTO modifyMapBlock(
      UUID pageId, UUID blockId, MapBlockDTO.Modify modifyMapBlockDTO) {
    MapBlock mapBlock =
        mapBlockRepository
            .findById(blockId)
            .orElseThrow(
                () -> {
                  log.error("존재하지 않는 blockId - blockId: {}", blockId);
                  return new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
                });

    if (modifyMapBlockDTO.getAddress() != null) mapBlock.setAddress(modifyMapBlockDTO.getAddress());
    if (modifyMapBlockDTO.getLatitude() != null)
      mapBlock.setLatitude(modifyMapBlockDTO.getLatitude());
    if (modifyMapBlockDTO.getLongitude() != null)
      mapBlock.setLongitude(modifyMapBlockDTO.getLongitude());
    if (modifyMapBlockDTO.getVisible() != null) mapBlock.setVisible(modifyMapBlockDTO.getVisible());

    mapBlockRepository.save(mapBlock);

    return BlockResponseDTO.fromEntity(mapBlock);
  }

  @Transactional
  public void deleteMapBlock(UUID blockId) {
    mapBlockRepository.deleteById(blockId);
  }
}
