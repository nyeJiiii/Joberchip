package kr.joberchip.server.v1._errors.exceptions;

import kr.joberchip.server.v1._utils.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiServerException extends RuntimeException {
  public ApiServerException(String message) {
    super(message);
  }

  public ApiServerException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiResponse.Result<Object> body() {
    return ApiResponse.error(getMessage(), status());
  }

  public HttpStatus status() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
