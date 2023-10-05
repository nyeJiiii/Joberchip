package kr.joberchip.server.v1.page.controller.dto;

import kr.joberchip.server.v1.page.controller.dto.BlockDTO;

import java.util.List;

public record MoveBlockDTO(List<BlockDTO> blocks) {}
