package kr.joberchip.server.v1.share.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.dto.create.CreateTextBlock;
import kr.joberchip.server.v1.share.block.dto.modify.ModifyTextBlock;
import kr.joberchip.server.v1.share.block.service.TextBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/textBlock")
public class TextBlockController {

  private final TextBlockService textBlockService;

  @PostMapping
  public ResponseEntity<ApiResponse.Result<Object>> createTextBlock(
      @PathVariable UUID pageId, @RequestBody CreateTextBlock createTextBlock) {

    textBlockService.createTextBlock(pageId, createTextBlock);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<Object> modifyTextBlock(
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      @RequestBody ModifyTextBlock modifyTextBlock) {
    textBlockService.modifyTextBlock(pageId, blockId, modifyTextBlock);

    return ApiResponse.success();
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> modifyTextBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId) {

    textBlockService.deleteTextBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
