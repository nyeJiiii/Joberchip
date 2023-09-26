package kr.joberchip.server.v1.share.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.dto.create.CreateVideoBlock;
import kr.joberchip.server.v1.share.block.dto.modify.ModifyVideoBlock;
import kr.joberchip.server.v1.share.block.service.VideoBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/videoBlock")
public class VideoBlockController {

  private final VideoBlockService videoBlockService;

  @PostMapping
  public ApiResponse.Result<Object> createVideoBlock(
      @PathVariable UUID pageId, @RequestParam CreateVideoBlock createVideoBlock) {

    videoBlockService.uploadVideo(createVideoBlock);
    videoBlockService.createVideoBlock(pageId, createVideoBlock);

    return ApiResponse.success();
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<Object> modifyVideoBlock(
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      @RequestBody ModifyVideoBlock modifyVideoBlock) {

    videoBlockService.modifyVideoBlock(pageId, blockId, modifyVideoBlock);

    return ApiResponse.success();
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteVideoBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId) {

    videoBlockService.deleteVideoBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
