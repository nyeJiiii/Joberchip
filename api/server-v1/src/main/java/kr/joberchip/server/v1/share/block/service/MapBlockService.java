package kr.joberchip.server.v1.share.block.service;

import kr.joberchip.core.share.block.MapBlock;
import kr.joberchip.server.v1.share.block.dto.create.CreateMapBlock;
import kr.joberchip.server.v1.share.block.repository.MapBlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapBlockService {

    private final MapBlockRepository mapBlockRepository;

    @Transactional
    public void createMapBlock(CreateMapBlock newMapBlock) {
        MapBlock mapBlock = mapBlockRepository.save(newMapBlock.toEntity());
        System.out.println("mapBlock.getMapBlockId() = " + mapBlock.getMapBlockId());
    }

    @Transactional
    public void modifyMapBlock(UUID blockId, CreateMapBlock modifiedMapBlock) {
        mapBlockRepository.updateById(
                modifiedMapBlock.getAddress(),
                modifiedMapBlock.getLatitude(),
                modifiedMapBlock.getLongitude(),
                blockId);
    }
}
