package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.TemplateBlockDTO;
import kr.joberchip.server.v1.block.service.TemplateBlockService;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  public ApiResponse.Result<Object> createTemplateBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @RequestBody TemplateBlockDTO templateBlockDTO) {

    log.info("[TemplateBlockController][POST] Current Username : {}", loginUser.user().getUsername());
    log.info("[TemplateBlockController][POST] Current Page Id : {}", pageId);
    log.info("[TemplateBlockController][POST] {}", templateBlockDTO);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = templateBlockService.createTemplateBlock(pageId, templateBlockDTO);

    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyTemplateBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @PathVariable UUID blockId,
          @RequestBody TemplateBlockDTO templateBlockDTO) {

    log.info("[TemplateBlockController][PUT] Current Username : {}", loginUser.user().getUsername());
    log.info("[TemplateBlockController][PUT] Current Page Id : {}", pageId);
    log.info("[TemplateBlockController][PUT] Target Block Id : {}", blockId);
    log.info("[TemplateBlockController][PUT] {}", templateBlockDTO);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = templateBlockService.modifyTemplateBlock(blockId, templateBlockDTO);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteTemplateBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @PathVariable UUID blockId) {

    log.info("[TemplateBlockController][DELETE] Current Username : {}", loginUser.user().getUsername());
    log.info("[TemplateBlockController][DELETE] Current Page Id : {}", pageId);
    log.info("[TemplateBlockController][DELETE] Target Block Id : {}", blockId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    templateBlockService.deleteTemplateBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
