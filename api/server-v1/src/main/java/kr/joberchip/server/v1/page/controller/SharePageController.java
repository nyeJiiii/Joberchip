package kr.joberchip.server.v1.page.controller;

import java.util.List;
import java.util.UUID;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.page.controller.dto.*;
import kr.joberchip.server.v1.page.repository.SharePagePrivilegeRepository;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import kr.joberchip.server.v1.page.service.SharePageService;
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
  private final SharePagePrivilegeRepository sharePagePrivilegeRepository;

  private final SharePageService sharePageService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  /**
   * 페이지 상세 조회
   *
   * @param pageId 페이지 ID
   * @return 페이지 상세 정보
   */
  @GetMapping("/{pageId}")
  public ApiResponse.Result<SharePageDetailResponseDTO> pageDetails(
      @AuthenticationPrincipal CustomUserDetails loginUser, @PathVariable UUID pageId) {

    SharePageDetailResponseDTO response =
        loginUser != null
            ? sharePageService.getDetails(loginUser.user().getUserId(), pageId)
            : sharePageService.getDetails(null, pageId);

    return ApiResponse.success(response);
  }

  @GetMapping("/{pageId}/blocks")
  public ApiResponse.Result<Object> moveBlocks(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @RequestBody List<BlockDTO> blocks) {

    log.info("Blocks : {}", blocks);

    return ApiResponse.success();
  }

  @GetMapping("/breadCrumbBar")
  public ApiResponse.Result<SharePageTreeResponseDTO.PageTreeNode> pageBreadCrumbBar(
      @RequestParam UUID pageId) {

    SharePageTreeResponseDTO.PageTreeNode response = sharePageService.getPageBreadCrumbBar(pageId);

    return ApiResponse.success(response);
  }

  /**
   * 페이지 생성
   *
   * @param userDetails 생성 요청자 정보 (현재 로그인 한 사용자)
   * @param createPageRequest 상위 페이지 ID, 페이지 제목, 설명
   * @return 생성된 페이지 정보
   */
  @PostMapping("/new")
  public ResponseEntity<ApiResponse.Result<SharePageProfileImageResponseDTO>> create(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody SharePageRequestDTO createPageRequest) {
    log.info("Create Share Page Request : {}", createPageRequest);

    UUID generatedPageId =
        sharePageService.create(userDetails.user().getUserId(), createPageRequest);
    sharePagePrivilegeService.registerPrivilege(
        userDetails.user().getUserId(), generatedPageId, PrivilegeType.EDIT);

    return ResponseEntity.ok(ApiResponse.success());
  }

  @PostMapping("/privilege")
  public ApiResponse.Result<SharePagePrivilegeDTO> modifyPrivilege(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @RequestBody SharePagePrivilegeDTO sharePagePrivilegeDTO) {

    log.info("Page Permission Setting : {}", sharePagePrivilegeDTO);

    sharePagePrivilegeService.modifyPrivilege(loginUser.user().getUserId(), sharePagePrivilegeDTO);

    return ApiResponse.success();
  }

  @GetMapping("/tree")
  public ApiResponse.Result<SharePageTreeResponseDTO.PageTreeNode> pageTree(
      @RequestParam UUID spaceId) {

    SharePageTreeResponseDTO.PageTreeNode response = sharePageService.getPageTree(spaceId);

    return ApiResponse.success(response);
  }
}
