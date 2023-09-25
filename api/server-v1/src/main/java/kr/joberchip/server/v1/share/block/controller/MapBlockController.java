package kr.joberchip.server.v1.share.block.controller;

import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.dto.create.CreateMapBlock;
import kr.joberchip.server.v1.share.block.service.MapBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/page/{pageId}/mapBlock")
@RequiredArgsConstructor
public class MapBlockController {

    private final MapBlockService mapBlockService;

    @PostMapping("/")
    public ResponseEntity<?> createMapBlock(@PathVariable UUID pageId, CreateMapBlock newMapBlock) {
        mapBlockService.createMapBlock(newMapBlock);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

}
