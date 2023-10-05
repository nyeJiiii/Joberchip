package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.VideoBlockDTO;
import kr.joberchip.server.v1.block.service.VideoBlockService;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/videoBlock")
public class VideoBlockController {

  private final VideoBlockService videoBlockService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  @PostMapping
  public ApiResponse.Result<BlockResponseDTO> createVideoBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      VideoBlockDTO videoBlockRequestDTO) {

    log.info("[TextBlockController] Login User : {}", loginUser);
    log.info("[TextBlockController] Current Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO responseDTO = videoBlockService.createVideoBlock(pageId, videoBlockRequestDTO);

    return ApiResponse.success(responseDTO);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyVideoBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId,
      VideoBlockDTO videoBlockRequestDTO) {

    log.info("[TextBlockController] Login User : {}", loginUser);
    log.info("[TextBlockController] Current Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    BlockResponseDTO response = videoBlockService.modifyVideoBlock(blockId, videoBlockRequestDTO);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteVideoBlock(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @PathVariable UUID blockId) {

    log.info("[TextBlockController] Login User : {}", loginUser);
    log.info("[TextBlockController] Current Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    videoBlockService.deleteVideoBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
