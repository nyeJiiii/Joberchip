package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.LinkBlockDTO;
import kr.joberchip.server.v1.block.service.LinkBlockService;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/linkBlock")
public class LinkBlockController {
  private final LinkBlockService linkBlockService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  @PostMapping
  public ApiResponse.Result<BlockResponseDTO> createLinkBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @RequestBody LinkBlockDTO linkBlockDTO) {

    log.info("[LinkBlockController][POST] Current Username : {}", loginUser.user().getUsername());
    log.info("[LinkBlockController][POST] Current Page Id : {}", pageId);
    log.info("[LinkBlockController][POST] {}", linkBlockDTO);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = linkBlockService.createLinkBlock(pageId, linkBlockDTO);

    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<Object> modifyLinkBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @PathVariable UUID blockId,
          @RequestBody LinkBlockDTO linkBlockDTO) {

    log.info("[LinkBlockController][PUT] Current Username : {}", loginUser.user().getUsername());
    log.info("[LinkBlockController][PUT] Current Page Id : {}", pageId);
    log.info("[LinkBlockController][PUT] Target Block Id : {}", blockId);
    log.info("[LinkBlockController][PUT] {}", linkBlockDTO);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO responseDTO = linkBlockService.modifyLinkBlock(blockId, linkBlockDTO);

    return ApiResponse.success(responseDTO);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteLinkBlock(
          @AuthenticationPrincipal CustomUserDetails loginUser,
          @PathVariable UUID pageId,
          @PathVariable UUID blockId) {

    log.info("[LinkBlockController][DELETE] Current Username : {}", loginUser.user().getUsername());
    log.info("[LinkBlockController][DELETE] Current Page Id : {}", pageId);
    log.info("[LinkBlockController][DELETE] Target Block Id : {}", blockId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);
    linkBlockService.deleteLinkBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
