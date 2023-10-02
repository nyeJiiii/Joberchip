package kr.joberchip.server.v1.share.block.controller;

import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.controller.dto.MapBlockDTO;
import kr.joberchip.server.v1.share.block.service.MapBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ApiResponse.Result<Object> createMapBlock(@PathVariable UUID pageId, @RequestBody @Valid MapBlockDTO.Create newMapBlock, Errors errors) {
        mapBlockService.createMapBlock(pageId, newMapBlock);
        return ApiResponse.success();
    }

    @PutMapping("/mapBlock/{blockId}")
    public ApiResponse.Result<Object> modifyMapBlock(@PathVariable UUID blockId, @RequestBody MapBlockDTO.Modify modifiedMapBlock) {
        mapBlockService.modifyMapBlock(blockId, modifiedMapBlock);
        return ApiResponse.success();
    }

    @GetMapping("/mapBlock/{blockId}")
    public ApiResponse.Result<Object> changeVisible(@PathVariable UUID blockId) {
//         MapBlockDTO.ReturnVisible changeVisible = ;
        return ApiResponse.success(mapBlockService.changeVisible(blockId));
    }

    @DeleteMapping("/mapBlock/{blockId}")
    public ApiResponse.Result<Object> deleteMapBlock(@PathVariable UUID blockId) {
        mapBlockService.deleteMapBlock(blockId);
        return ApiResponse.success();
    }

}
