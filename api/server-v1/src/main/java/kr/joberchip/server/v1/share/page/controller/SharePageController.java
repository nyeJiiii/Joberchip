package kr.joberchip.server.v1.share.page.controller;

import java.util.UUID;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.share.page.dto.SharePageSummaryResponse;
import kr.joberchip.server.v1.share.page.dto.request.CreatePageRequest;
import kr.joberchip.server.v1.share.page.dto.request.ModifyPagePermission;
import kr.joberchip.server.v1.share.page.dto.response.PageTreeResponse;
import kr.joberchip.server.v1.share.page.dto.response.SharePageDetailResponse;
import kr.joberchip.server.v1.share.page.service.SharePageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/page")
@RequiredArgsConstructor
public class SharePageController {

  private final SharePageService sharePageService;

  /**
   * 페이지 상세 조회
   *
   * @param pageId 페이지 ID
   * @return 페이지 상세 정보
   */
  @GetMapping("/{pageId}")
  public ResponseEntity<ApiResponse.Result<SharePageDetailResponse>> pageDetails(
      @PathVariable UUID pageId) {

    SharePageDetailResponse response = sharePageService.getDetails(pageId);

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  /**
   * 페이지 생성
   *
   * @param userDetails 생성 요청자 정보 (현재 로그인 한 사용자)
   * @param createPageRequest 상위 페이지 ID, 페이지 제목, 설명
   * @return 생성된 페이지 정보
   */
  @PostMapping("/new")
  public ResponseEntity<ApiResponse.Result<SharePageSummaryResponse>> create(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody CreatePageRequest createPageRequest) {
    log.info("Create Share Page Request : {}", createPageRequest);

    SharePageSummaryResponse sharePageSummaryResponse =
        sharePageService.create(userDetails.user().getUserId(), createPageRequest);

    return ResponseEntity.ok(ApiResponse.success(sharePageSummaryResponse));
  }

  @PostMapping("/permission")
  public ResponseEntity<ApiResponse.Result<Object>> modifyPagePermission(
      @RequestBody ModifyPagePermission modifyPagePermission) {
    log.info("Page Permission Setting : {}", modifyPagePermission);

    sharePageService.modifyPagePermission(modifyPagePermission);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @GetMapping("/tree")
  public ResponseEntity<ApiResponse.Result<PageTreeResponse.PageTreeNode>> pageTree(
      @RequestParam UUID spaceId) {
    return ResponseEntity.ok(ApiResponse.success(sharePageService.getPageTree(spaceId)));
  }
}
