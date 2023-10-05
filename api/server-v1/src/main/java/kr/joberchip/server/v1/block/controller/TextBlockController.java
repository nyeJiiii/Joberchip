package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.TextBlockDTO;
import kr.joberchip.server.v1.block.service.TextBlockService;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/textBlock")
public class TextBlockController {

  private final SharePagePrivilegeService sharePagePrivilegeService;
  private final TextBlockService textBlockService;

  @PostMapping
  public ApiResponse.Result<BlockResponseDTO> createTextBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @RequestBody TextBlockDTO textBlockDTO) {
    log.info("[TextBlockController] Login User : {}", loginUser);
    log.info("[TextBlockController] Current Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = textBlockService.createTextBlock(pageId, textBlockDTO);

    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyTextBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      @RequestBody TextBlockDTO textBlockDTO) {

    log.info("[TextBlockController] Login User : {}", loginUser);
    log.info("[TextBlockController] Current Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = textBlockService.modifyTextBlock(blockId, textBlockDTO);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteTextBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId) {
    log.info("[TextBlockController] Login User : {}", loginUser);
    log.info("[TextBlockController] Current Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    textBlockService.deleteTextBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
