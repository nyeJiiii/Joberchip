package kr.joberchip.server.v1._errors;

import kr.joberchip.server.v1._errors.exceptions.ApiServerException;
import kr.joberchip.server.v1._utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ApiServerException.class})
  public ResponseEntity<ApiResponse.Result<Object>> globalException(ApiServerException ase) {
    return new ResponseEntity<>(ase.body(), ase.status());
  }
}
