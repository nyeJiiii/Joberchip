package kr.joberchip.server.v1.share.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.dto.create.CreateLinkBlock;
import kr.joberchip.server.v1.share.block.dto.modify.ModifyLinkBlock;
import kr.joberchip.server.v1.share.block.service.LinkBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/linkBlock")
public class LinkBlockController {
  private final LinkBlockService linkBlockService;

  @PostMapping
  public ResponseEntity<ApiResponse.Result<Object>> createLinkBlock(
      @PathVariable UUID pageId, @RequestBody CreateLinkBlock createLinkBlock) {

    linkBlockService.createLinkBlock(pageId, createLinkBlock);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<Object> modifyLinkBlock(
      @PathVariable UUID pageId,
      @PathVariable Long blockId,
      @RequestBody ModifyLinkBlock modifyLinkBlock) {

    return ApiResponse.success();
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteLinkBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId) {

    linkBlockService.deleteLinkBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
