package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.ImageBlockDTO;
import kr.joberchip.server.v1.block.service.ImageBlockService;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/page/{pageId}/imageBlock")
@RequiredArgsConstructor
public class ImageBlockController {
  private final ImageBlockService imageBlockService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  @PostMapping
  public ApiResponse.Result<BlockResponseDTO> createImageBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      ImageBlockDTO imageBlockDTO) {

    log.info("[ImageBlockController][POST] Current Username : {}", loginUser.user().getUsername());
    log.info("[ImageBlockController][POST] Current Page Id : {}", pageId);
    log.info("[ImageBlockController][POST] {}", imageBlockDTO);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = imageBlockService.createImageBlock(pageId, imageBlockDTO);

    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyImageBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      ImageBlockDTO imageBlockDTO) {

    log.info("[ImageBlockController][PUT] Current Username : {}", loginUser.user().getUsername());
    log.info("[ImageBlockController][PUT] Current Page Id : {}", pageId);
    log.info("[ImageBlockController][PUT] Target Block Id : {}", blockId);
    log.info("[ImageBlockController][PUT] {}", imageBlockDTO);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = imageBlockService.modifyImageBlock(blockId, imageBlockDTO);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteImageBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId) {

    log.info("[ImageBlockController][DELETE] Current Username : {}", loginUser.user().getUsername());
    log.info("[ImageBlockController][DELETE] Current Page Id : {}", pageId);
    log.info("[ImageBlockController][DELETE] Target Block Id : {}", blockId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);
    imageBlockService.deleteImageBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
