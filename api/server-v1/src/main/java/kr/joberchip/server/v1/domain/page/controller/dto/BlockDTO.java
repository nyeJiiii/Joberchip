package kr.joberchip.server.v1.domain.page.controller.dto;

import java.util.UUID;
import kr.joberchip.server.v1.domain.block.controller.dto.BlockType;

public record BlockDTO(
    UUID blockId, BlockType blockType, Integer x, Integer y, Integer w, Integer h) {}
