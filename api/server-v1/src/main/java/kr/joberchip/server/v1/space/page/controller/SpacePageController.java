package kr.joberchip.server.v1.space.page.controller;

import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.space.page.dto.CreatePageRequest;
import kr.joberchip.server.v1.space.page.service.SpacePageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/space/{spaceId}")
@RequiredArgsConstructor
public class SpacePageController {

  private final SpacePageService spacePageService;

  @GetMapping("/page/{pageId}")
  public ApiResponse.Result<Object> getPage(@PathVariable Long spaceId, @PathVariable Long pageId) {

    return ApiResponse.success();
  }

  @PostMapping("/page")
  public ApiResponse.Result<Object> createPage(
      @PathVariable Long spaceId, @RequestBody CreatePageRequest pageRequestDTO) {

    return ApiResponse.success();
  }
}
