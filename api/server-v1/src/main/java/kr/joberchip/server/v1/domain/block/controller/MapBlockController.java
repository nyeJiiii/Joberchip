package kr.joberchip.server.v1.domain.block.controller;

import java.util.UUID;
import javax.validation.Valid;

import kr.joberchip.server.v1.domain.block.controller.dto.MapBlockDTO;
import kr.joberchip.server.v1.domain.block.service.MapBlockService;
import kr.joberchip.server.v1.domain.page.service.SharePagePrivilegeService;
import kr.joberchip.server.v1.global.security.CustomUserDetails;
import kr.joberchip.server.v1.global.utils.ApiResponse;
import kr.joberchip.server.v1.domain.block.controller.dto.BlockResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/page/{pageId}/mapBlock")
@RequiredArgsConstructor
public class MapBlockController {

  private final MapBlockService mapBlockService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  @PostMapping
  public ApiResponse.Result<Object> createMapBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @RequestBody @Valid MapBlockDTO newMapBlock,
          Errors errors) {

    log.info("[MapBlockController][POST] Current Username : {}", loginUser.user().getUsername());
    log.info("[MapBlockController][POST] Current Page Id : {}", pageId);
    log.info("[MapBlockController][POST] {}", newMapBlock);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);
    BlockResponseDTO response = mapBlockService.createMapBlock(pageId, newMapBlock);
    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyMapBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @PathVariable UUID blockId,
          @RequestBody MapBlockDTO modifiedMapBlock) {

    log.info("[MapBlockController][PUT] Current Username : {}", loginUser.user().getUsername());
    log.info("[MapBlockController][PUT] Current Page Id : {}", pageId);
    log.info("[MapBlockController][PUT] Target Block Id : {}", blockId);
    log.info("[MapBlockController][PUT] {}", modifiedMapBlock);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = mapBlockService.modifyMapBlock(blockId, modifiedMapBlock);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteMapBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @PathVariable UUID blockId) {

    log.info("[MapBlockController][DELETE] Current Username : {}", loginUser.user().getUsername());
    log.info("[MapBlockController][DELETE] Current Page Id : {}", pageId);
    log.info("[MapBlockController][DELETE] Target Block Id : {}", blockId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);
    mapBlockService.deleteMapBlock(pageId, blockId);
    return ApiResponse.success();
  }
}
