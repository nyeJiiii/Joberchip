package kr.joberchip.server.v1.share.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.block.controller.dto.TextBlockDTO;
import kr.joberchip.server.v1.share.block.service.TextBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page")
public class TextBlockController {

  private final TextBlockService textBlockService;

  @PostMapping("/{pageId}/textBlock")
  public ApiResponse.Result<Object> createTextBlock(
          @PathVariable UUID pageId,
          @RequestBody @Valid TextBlockDTO.Create newTextBlock,
          Errors errors) {

    textBlockService.createTextBlock(pageId, newTextBlock);

    return ApiResponse.success();
  }

  @PutMapping("/textBlock/{blockId}")
  public ApiResponse.Result<Object> modifyTextBlock(
      @PathVariable UUID blockId,
      @RequestBody TextBlockDTO.Modify modifiedTextBlock) {
    textBlockService.modifyTextBlock(blockId, modifiedTextBlock);
    return ApiResponse.success();
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> modifyTextBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId) {

    textBlockService.deleteTextBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
