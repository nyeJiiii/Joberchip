package kr.joberchip.server.v1.share.block.controller;

import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.dto.create.CreateMapBlock;
import kr.joberchip.server.v1.share.block.service.MapBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/page")
@RequiredArgsConstructor
public class MapBlockController {

    private final MapBlockService mapBlockService;

    @PostMapping("/{pageId}/mapBlock")
    public ResponseEntity<?> createMapBlock(@PathVariable UUID pageId, @RequestBody @Valid CreateMapBlock newMapBlock, Errors errors) {
        mapBlockService.createMapBlock(pageId, newMapBlock);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @PutMapping("/mapBlock/{blockId}")
    public ResponseEntity<?> modifyMapBlock(@PathVariable UUID blockId, @RequestBody @Valid CreateMapBlock modifiedMapBlock, Errors errors) {
        mapBlockService.modifyMapBlock(blockId, modifiedMapBlock);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

    @DeleteMapping("/mapBlock/{blockId}")
    public ResponseEntity<?> deleteMapBlock(@PathVariable UUID blockId) {
        mapBlockService.deleteMapBlock(blockId);
        return ResponseEntity.ok().body(ApiResponse.success());
    }

}
