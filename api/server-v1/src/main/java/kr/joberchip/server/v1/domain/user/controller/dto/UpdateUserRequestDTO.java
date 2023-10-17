package kr.joberchip.server.v1.domain.user.controller.dto;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUserRequestDTO(
    @RequestParam String nickname, @RequestPart MultipartFile profileImageLink) {}
