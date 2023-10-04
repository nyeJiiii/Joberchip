package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import javax.validation.Valid;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.MapBlockDTO;
import kr.joberchip.server.v1.block.service.MapBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/page/{pageId}/mapBlock")
@RequiredArgsConstructor
public class MapBlockController {

  private final MapBlockService mapBlockService;

  @PostMapping
  public ApiResponse.Result<Object> createMapBlock(
      @PathVariable UUID pageId,
      @RequestBody @Valid MapBlockDTO.Create newMapBlock,
      Errors errors) {
    BlockResponseDTO response = mapBlockService.createMapBlock(pageId, newMapBlock);
    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyMapBlock(
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      @RequestBody MapBlockDTO.Modify modifiedMapBlock) {
    BlockResponseDTO response = mapBlockService.modifyMapBlock(pageId, blockId, modifiedMapBlock);
    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteMapBlock(@PathVariable UUID blockId) {
    mapBlockService.deleteMapBlock(blockId);
    return ApiResponse.success();
  }
}
