package kr.joberchip.server.v1.share.block.service;

import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.core.share.page.SharePage;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1.share.block.dto.create.CreateMapBlock;
import kr.joberchip.server.v1.share.block.repository.MapBlockRepository;
import kr.joberchip.server.v1.share.page.repository.SharePageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapBlockService {

    private final MapBlockRepository mapBlockRepository;
    private final SharePageRepository sharePageRepository;

    @Transactional
    public void createMapBlock(UUID pageId, CreateMapBlock newMapBlock) {
        SharePage parentPage = isPage(pageId);
        MapBlock savedmapBlock =
                mapBlockRepository.save(
                        newMapBlock.setEntity(new MapBlock())
                );
        parentPage.addMapBlock(savedmapBlock);
        log.info("UUID of NEW MAP BLOCK: " + savedmapBlock.getMapBlockId());
    }

    @Transactional
    public void modifyMapBlock(UUID blockId, CreateMapBlock modifiedMapBlock) {
        MapBlock mapBlock = isBlock(blockId);
        modifiedMapBlock.setEntity(mapBlock);
    }

    @Transactional
    public void deleteMapBlock(UUID blockId) {
        isBlock(blockId);
        mapBlockRepository.deleteById(blockId);
    }

    private SharePage isPage(UUID pageId) {
        return sharePageRepository.findById(pageId).orElseThrow(() -> {
            log.error("존재하지 않는 pageID - pageId: {}", pageId);
            throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
        });
    }

    private MapBlock isBlock(UUID blockId) {
        return mapBlockRepository.findById(blockId).orElseThrow(() -> {
            log.error("존재하지 않는 blockId - blockId: {}", blockId);
            throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
        });
    }
}
