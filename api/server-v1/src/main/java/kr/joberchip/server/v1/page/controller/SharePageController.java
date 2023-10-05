package kr.joberchip.server.v1.page.controller;

import java.util.UUID;
import javax.validation.Valid;
import kr.joberchip.core.page.types.PrivilegeType;
import kr.joberchip.server.v1._config.security.CustomUserDetails;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1._utils.ApiResponse;
import kr.joberchip.server.v1.page.controller.dto.*;
import kr.joberchip.server.v1.page.repository.SharePageRepository;
import kr.joberchip.server.v1.page.service.SharePagePrivilegeService;
import kr.joberchip.server.v1.page.service.SharePageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/page")
@RequiredArgsConstructor
public class SharePageController {
  private final SharePageService sharePageService;
  private final SharePagePrivilegeService sharePagePrivilegeService;

  /**
   * 페이지 상세 조회 - 미인증 사용자도 접근 가능
   *
   * @param pageId 페이지 ID
   * @return 페이지 상세 정보
   */
  @GetMapping("/{pageId}")
  public ApiResponse.Result<SharePageDetailResponseDTO> pageDetails(
      @AuthenticationPrincipal CustomUserDetails loginUser, @PathVariable UUID pageId) {

    log.info("[SharePageController] CustomUserDetails : {}", loginUser);
    log.info("[SharePageController] pageId: {}", pageId);

    if (loginUser == null) {
      SharePageDetailResponseDTO response = sharePageService.getDetails(pageId);
      return ApiResponse.success(response);
    }

    SharePageDetailResponseDTO response =
        sharePageService.getDetails(loginUser.user().getUserId(), pageId);

    log.info("[SharePageController] response : {}", response);

    return ApiResponse.success(response);
  }

  @PostMapping("/{pageId}/blocks")
  public ApiResponse.Result<Object> moveBlocks(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @RequestBody MoveBlockDTO moveBlockDTO) {

    log.info("[SharePageController] CustomUserDetails : {}", loginUser);
    log.info("[SharePageController] pageId: {}", pageId);

    sharePagePrivilegeService.checkPrivilege(
        loginUser.user().getUserId(), pageId, PrivilegeType.EDIT);

    log.info("Blocks : {}", moveBlockDTO);

    sharePageService.moveBlocks(moveBlockDTO);

    return ApiResponse.success();
  }

  @GetMapping("/{pageId}/breadCrumbBar")
  public ApiResponse.Result<SharePageTreeResponseDTO.PageTreeNode> pageBreadCrumbBar(
      @PathVariable UUID pageId) {

    SharePageTreeResponseDTO.PageTreeNode response = sharePageService.getPageBreadCrumbBar(pageId);

    return ApiResponse.success(response);
  }

  /**
   * 페이지 생성
   *
   * @param loginUser 생성 요청자 정보 (현재 로그인 한 사용자)
   * @param createSharePageRequestDTO 상위 페이지 ID, 페이지 제목, 설명
   * @return 생성된 페이지 정보
   */
  @PostMapping("/new")
  public ApiResponse.Result<SharePageProfileImageResponseDTO> createSharePage(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @RequestBody @Valid SharePageRequestDTO createSharePageRequestDTO,
      Errors errors) {

    log.info("[SharePageController] CustomUserDetails : {}", loginUser);
    log.info("[SharePageController] SharePageRequestDTO : {}", createSharePageRequestDTO);

    // 하위 페이지를 생성하기 위해서는 선택된 페이지에 대한 수정 권한이 있어야 함.
    sharePagePrivilegeService.checkEditPrivilege(
        loginUser.user().getUserId(), createSharePageRequestDTO.parentPageId());

    UUID generatedPageId = sharePageService.create(createSharePageRequestDTO);
    log.info("[SharePageController] Generated SharePageId : {}", generatedPageId);

    sharePagePrivilegeService.registerPrivilege(
        loginUser.user().getUserId(), generatedPageId, PrivilegeType.EDIT);

    return ApiResponse.success();
  }

  @PutMapping("/{pageId}")
  public ApiResponse.Result<SharePageDetailResponseDTO> modifySharePage(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable UUID pageId,
      @Valid SharePageModifyDTO sharePageModifyDTO,
      Errors errors) {

    if (loginUser == null) throw new ApiClientException(ErrorMessage.UN_AUTHORIZED);

    log.info("[SharePageController] CustomUserDetails : {}", loginUser);
    log.info("[SharePageController] SharePageRequestDTO : {}", sharePageModifyDTO);
    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    SharePageDetailResponseDTO response =
        sharePageService.modify(pageId, sharePageModifyDTO);

    return ApiResponse.success(response);
  }

  @PostMapping("/privilege")
  public ApiResponse.Result<SharePagePrivilegeDTO> modifyPrivilege(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @RequestBody SharePagePrivilegeDTO sharePagePrivilegeDTO,
      Errors errors) {

    log.info("[SharePageController] Login User : {}", loginUser);
    log.info("[SharePageController] SharePage Privilege DTO : {}", sharePagePrivilegeDTO);

    // 편집 권한이 있는 사용자만 다른 사용자에게 페이지 권한 부여 가능
    sharePagePrivilegeService.checkEditPrivilege(
        loginUser.user().getUserId(), sharePagePrivilegeDTO.sharePageId());

    sharePagePrivilegeService.modifyPrivilege(sharePagePrivilegeDTO);

    return ApiResponse.success();
  }

  @GetMapping("/tree")
  public ApiResponse.Result<SharePageTreeResponseDTO.PageTreeNode> pageTree(
      @RequestParam UUID spaceId) {
    log.info("");

    SharePageTreeResponseDTO.PageTreeNode response = sharePageService.getPageTree(spaceId);

    return ApiResponse.success(response);
  }

  @DeleteMapping("/{pageId}")
  public ApiResponse.Result<Object> deleteSharePage(
      @AuthenticationPrincipal CustomUserDetails loginUser, @PathVariable UUID pageId) {
    log.info("[SharePageController] Login User : {}", loginUser);
    log.info("[SharePageController] Target Page Id : {}", pageId);

    sharePagePrivilegeService.checkEditPrivilege(loginUser.user().getUserId(), pageId);

    sharePageService.delete(pageId);

    return ApiResponse.success();
  }
}
