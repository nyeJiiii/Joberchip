package kr.joberchip.auth.v1.errors.exception;

import kr.joberchip.auth.v1._utils.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/** Authorization failed exception */
@Getter
public class Exception403 extends RuntimeException {

  public Exception403(String message) {
    super(message);
  }

  public ApiResponse.Result<Object> body() {
    return ApiResponse.error(getMessage(), status());
  }

  public HttpStatus status() {
    return HttpStatus.FORBIDDEN;
  }
}
