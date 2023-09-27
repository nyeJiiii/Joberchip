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
}
