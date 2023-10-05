package kr.joberchip.server.v1.page.controller.dto;

import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record SharePageModifyDTO(
    @RequestParam UUID parentPageId,
    @RequestParam String title,
    @RequestParam String description,
    @RequestPart MultipartFile profileImage,
    @RequestParam Boolean visible) {}
