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
        // pageId를 조회해 해당 페이지가 존재하는지 확인
        SharePage parentPage =
                sharePageRepository.findById(pageId).orElseThrow(() -> {
                    log.error("존재하지 않는 pageID - pageId: {}", pageId);
                    throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
                });

        // pageID가 존재한다면 mapBlock의 내용을 Entity로 변환, 위치정보 설정
        MapBlock mapBlock = newMapBlock.toEntity();
        mapBlock.setX(newMapBlock.getX());
        mapBlock.setY(newMapBlock.getY());
        mapBlock.setHeight(newMapBlock.getHeight());
        mapBlock.setWidth(newMapBlock.getWidth());

        // 생성된 mapBlock을 mapBlockRepository에 save
        MapBlock savedmapBlock = mapBlockRepository.save(mapBlock);

        // Entity로 저장된 mapBlock의 parentPage값 참조
        // 해당 페이지의 Set<MapBlock>에 add
        parentPage.addMapBlock(savedmapBlock);

        log.info("UUID of NEW MAP BLOCK: " + savedmapBlock.getMapBlockId());
    }

    @Transactional
    public void modifyMapBlock(UUID blockId, CreateMapBlock modifiedMapBlock) {
        isBlock(blockId);
        mapBlockRepository.updateAllById(
                modifiedMapBlock.getAddress(),
                modifiedMapBlock.getLatitude(),
                modifiedMapBlock.getLongitude(),
                modifiedMapBlock.getX(),
                modifiedMapBlock.getY(),
                modifiedMapBlock.getHeight(),
                modifiedMapBlock.getWidth(),
                blockId);
    }

    @Transactional
    public void deleteMapBlock(UUID blockId) {
        isBlock(blockId);
        mapBlockRepository.deleteById(blockId);
    }

    private void isBlock(UUID blockId) {
        mapBlockRepository.findById(blockId).orElseThrow(() -> {
            log.error("존재하지 않는 blockId - blockId: {}", blockId);
            throw new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND);
        });
    }
}
