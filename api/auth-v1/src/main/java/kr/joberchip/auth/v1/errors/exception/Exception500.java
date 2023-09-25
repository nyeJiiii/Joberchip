package kr.joberchip.auth.v1.errors.exception;

import kr.joberchip.auth.v1._utils.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/** internal server error exception */
@Getter
public class Exception500 extends RuntimeException {

  public Exception500(String message) {
    super(message);
  }

  public ApiResponse.Result<Object> body() {
    return ApiResponse.error(getMessage(), status());
  }

  public HttpStatus status() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
