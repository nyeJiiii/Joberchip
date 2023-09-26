package kr.joberchip.server.v1.share.block.service;

import kr.joberchip.server.v1.share.block.controller.dto.TemplateBlockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TemplateBlockService {
    public void createTemplateBlock(UUID pageId, TemplateBlockDTO templateBlockDTO) {
    }

    public void deleteTemplateBlock(UUID pageId, UUID blockId) {
    }
}
