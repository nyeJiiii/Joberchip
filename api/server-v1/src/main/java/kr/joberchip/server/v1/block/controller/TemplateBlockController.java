package kr.joberchip.server.v1.block.controller;

import java.util.UUID;

import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.TemplateBlockDTO;
import kr.joberchip.server.v1.block.service.TemplateBlockService;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/templateBlock")
public class TemplateBlockController {
  private final TemplateBlockService templateBlockService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  @PostMapping
  public ResponseEntity<ApiResponse.Result<Object>> createTemplateBlock(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable UUID pageId,
      @RequestBody TemplateBlockDTO templateBlockDTO) {
    log.info("[TemplateBlockController] Page Id : {}", pageId);
    log.info("[TemplateBlockController] TemplateBlockDTO : {}", templateBlockDTO);

    templateBlockService.createTemplateBlock(
        customUserDetails.user().getUserId(), pageId, templateBlockDTO);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyLinkBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      @RequestBody TemplateBlockDTO templateBlockDTO) {

    log.info("[TemplateBlockController] Login User : {}", loginUser.user());
    log.info("[TemplateBlockController] Page Id : {}", pageId);
    log.info("[TemplateBlockController] Template Block Id : {}", blockId);
    log.info("[TemplateBlockController] TemplateBlockDTO : {}", templateBlockDTO);

    BlockResponseDTO response =
        templateBlockService.modifyTemplateBlock(
            loginUser.user().getUserId(), pageId, blockId, templateBlockDTO);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteLinkBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId) {

    log.info("[TemplateBlockController] Login User : {}", loginUser.user());
    log.info("[TemplateBlockController] Page Id : {}", pageId);
    log.info("[TemplateBlockController] Template Block Id : {}", blockId);

    sharePagePrivilegeService.checkPrivilege(
        loginUser.user().getUserId(), pageId, PrivilegeType.EDIT);


    templateBlockService.deleteTemplateBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
