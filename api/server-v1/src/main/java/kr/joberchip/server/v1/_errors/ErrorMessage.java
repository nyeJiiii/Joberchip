package kr.joberchip.server.v1._errors;

public class ErrorMessage {
  // 인증 - 토큰 관련
  public static final String TOKEN_UN_AUTHORIZED = "미인증 토큰";
  public static final String TOKEN_EXPIRED = "만료된 토큰";
  public static final String TOKEN_VERIFICATION_FAILED = "토큰 검증 실패";

  // 인증 - API 접근
  public static final String UN_AUTHORIZED = "인증 되지 않은 접근";
  public static final String FORBIDDEN = "접근 거부";

  // @Valid
  public static final String NOT_EMPTY = "비어있을 수 없습니다.";

  public static final String ENTITY_NOT_FOUND = "해당 페이지를 찾을 수 없습니다.";

  // 회원가입
  public static final String DUPLICATED_USERNAME = "이미 사용중인 아이디입니다.";
  // 로그인
  public static final String USER_NOT_FOUND = "아이디 또는 비밀번호가 올바르지 않습니다.";

  // 조회 관련
  public static final String USER_ENTITY_NOT_FOUND = "해당 유저를 찾을 수 없습니다.";
  public static final String SHARE_PAGE_ENTITY_NOT_FOUND = "해당 페이지를 찾을 수 없습니다.";
  public static final String BLOCK_ENTITY_NOT_FOUND = "해당 블록를 찾을 수 없습니다.";
  public static final String PRIVILEGE_ENTITY_NOT_FOUND = "권한이 등록되어 있지 않습니다.";
  public static final String SPACE_ENTITY_NOT_FOUND = "해당 스페이스를 찾을 수 없습니다.";
  public static final String NOT_VISIBLE_SHARE_PAGE= "비공개 페이지.";

  // 스페이스 관련
  public static final String ALREADY_INVITED_USER = "해당 스페이스 참여 중인 유저입니다.";
  public static final String NOT_SPACE_OWNER = "해당 스페이스 소유자가 아닙니다.";
  public static final String NO_SPACE_PARTICIPATION_INFO = "해당 스페이스 참여 정보가 없습니다.";
  public static final String DEFAULT_SPACE_CANNOT_REMOVE = "기본 스페이스는 삭제할 수 없습니다.";

  // 페이지 권한 관련
  public static final String NO_PRIVILEGE = "해당 권한이 없습니다.";
  public static final String INVALID_PRIVILEGE = "해당 권한이 없습니다.";
  public static final String EDIT_PRIVILEGE_NOT_FOUND = "해당 페이지에 대한 편집 권한이 없습니다.";

  // 비디오 블록 관련
  public static final String DUPLICATED_LINK_AND_ATTACHED_FILE = "링크와 파일을 동시에 첨부할 수 없습니다.";
  public static final String INVALID_VIDEO_BLOCK_REQUEST = "링크 또는 파일을 첨부해야 합니다.";

  // 파일 첨부 관련
  public static final String INVALID_FILE_EXTENSION = "첨부할 수 없는 파일 확장자입니다.";
}
