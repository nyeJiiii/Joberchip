package kr.joberchip.server.v1.block.controller;

import java.util.UUID;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.block.controller.dto.BlockResponseDTO;
import kr.joberchip.server.v1.block.controller.dto.TextBlockDTO;
import kr.joberchip.server.v1.block.service.TextBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/page/{pageId}/textBlock")
public class TextBlockController {

  private final TextBlockService textBlockService;

  @PostMapping
  public ApiResponse.Result<BlockResponseDTO> createTextBlock(
      @PathVariable UUID pageId, TextBlockDTO textBlockDTO) {

    BlockResponseDTO response = textBlockService.createTextBlock(pageId, textBlockDTO);

    return ApiResponse.success(response);
  }

  @PutMapping("/{blockId}")
  public ApiResponse.Result<BlockResponseDTO> modifyTextBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId, TextBlockDTO textBlockDTO) {

    BlockResponseDTO response = textBlockService.modifyTextBlock(pageId, blockId, textBlockDTO);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{blockId}")
  public ApiResponse.Result<Object> deleteTextBlock(
      @PathVariable UUID pageId, @PathVariable UUID blockId) {

    textBlockService.deleteTextBlock(pageId, blockId);

    return ApiResponse.success();
  }
}
