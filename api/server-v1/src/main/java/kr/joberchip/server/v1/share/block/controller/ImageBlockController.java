package kr.joberchip.server.v1.share.block.controller;

import kr.joberchip.server.v1._utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/space/{spaceId}/page/{pageId}/block")
public class ImageBlockController {

  @PostMapping("/image")
  public ApiResponse.Result<Object> createTextBlock(
      @PathVariable Long spaceId, @PathVariable Long pageId, @RequestBody MultipartFile file) {

    return ApiResponse.success();
  }

  @PutMapping("/image/{blockId}")
  public ApiResponse.Result<Object> modifyImageBlock(
      @PathVariable Long spaceId,
      @PathVariable Long pageId,
      @PathVariable Long blockId,
      @RequestBody MultipartFile file) {

    return ApiResponse.success();
  }
}
