package kr.joberchip.auth.v1.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

  /** @Valid **/
  public static final String NOT_EMPTY = "비어있을 수 없습니다.";

  /** 회원가입 **/
  public static final String DUPLICATED_USERNAME = "이미 사용중인 아이디입니다.";
  /** 로그인 **/
  public static final String USER_NOT_FOUND = "아이디 또는 비밀번호가 올바르지 않습니다.";

}
