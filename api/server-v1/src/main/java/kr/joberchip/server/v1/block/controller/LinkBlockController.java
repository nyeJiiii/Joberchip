package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.LinkBlockRequestDTO;
import kr.joberchip.server.v1.block.service.LinkBlockService;
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
      @PathVariable UUID pageId, @RequestBody LinkBlockRequestDTO createLinkBlock) {

    BlockResponseDTO responseDTO = linkBlockService.createLinkBlock(pageId, createLinkBlock);

    return ResponseEntity.ok(ApiResponse.success(responseDTO));
  }

  @PutMapping("/{blockId}")
  public ResponseEntity<ApiResponse.Result<Object>> modifyLinkBlock(
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      @RequestBody LinkBlockRequestDTO modifyRequestDTO) {

    BlockResponseDTO responseDTO = linkBlockService.modifyLinkBlock(blockId, modifyRequestDTO);

    return ResponseEntity.ok(ApiResponse.success(responseDTO));
  }

  @DeleteMapping("/{blockId}")
  public ResponseEntity<ApiResponse.Result<Object>> deleteLinkBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId) {

    linkBlockService.deleteLinkBlock(pageId, blockId);

    return ResponseEntity.ok(ApiResponse.success());
  }
}
