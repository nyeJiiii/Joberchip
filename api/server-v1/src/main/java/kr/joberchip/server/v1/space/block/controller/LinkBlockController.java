package kr.joberchip.server.v1.space.block.controller;

import kr.joberchip.server.v1._utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/space/{spaceId}/page/{pageId}/block")
public class LinkBlockController {

  @PostMapping("/link")
  public ApiResponse.Result<Object> createLinkBlock(
      @PathVariable Long spaceId, @PathVariable Long pageId, @RequestBody MultipartFile file) {

    return ApiResponse.success();
  }

  @PutMapping("/link/{blockId}")
  public ApiResponse.Result<Object> modifyLinkBlock(
      @PathVariable Long spaceId,
      @PathVariable Long pageId,
      @PathVariable Long blockId,
      @RequestBody MultipartFile file) {

    return ApiResponse.success();
  }
}
