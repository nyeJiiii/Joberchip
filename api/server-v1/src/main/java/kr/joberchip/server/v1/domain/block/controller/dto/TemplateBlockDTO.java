package kr.joberchip.server.v1.domain.block.controller.dto;

import kr.joberchip.core.block.TemplateBlock;

public record TemplateBlockDTO(
    String title, String description, Integer x, Integer y, Integer w, Integer h, Boolean visible) {
  public TemplateBlock toEntity() {
    return TemplateBlock.of(title, description, x, y, w, h);
  }
}
