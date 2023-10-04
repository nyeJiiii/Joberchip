package kr.joberchip.server.v1.block.controller.dto;

import kr.joberchip.core.block.TextBlock;
import org.springframework.web.bind.annotation.RequestParam;

public record TextBlockDTO(
       @RequestParam String content,
       @RequestParam Integer x,
       @RequestParam Integer y,
       @RequestParam Integer w,
       @RequestParam Integer h,
       @RequestParam Boolean visible) {
  public TextBlock toEntity() {
    return TextBlock.of(content, x, y, w, h, visible);
  }
}
