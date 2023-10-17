package kr.joberchip.server.v1.global.errors.exceptions.filter;

import kr.joberchip.server.v1.global.utils.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception401 extends RuntimeException {

  public Exception401(String message) {
    super(message);
  }

  public ApiResponse.Result<Object> body() {
    return ApiResponse.error(getMessage(), status());
  }

  public HttpStatus status() {
    return HttpStatus.UNAUTHORIZED;
  }
}
