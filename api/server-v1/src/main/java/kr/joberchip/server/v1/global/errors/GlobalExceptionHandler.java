package kr.joberchip.server.v1.global.errors;

import kr.joberchip.server.v1.global.errors.exceptions.ApiClientException;
import kr.joberchip.server.v1.global.errors.exceptions.ApiServerException;
import kr.joberchip.server.v1.global.errors.exceptions.filter.Exception400;
import kr.joberchip.server.v1.global.errors.exceptions.filter.Exception401;
import kr.joberchip.server.v1.global.errors.exceptions.filter.Exception403;
import kr.joberchip.server.v1.global.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ApiClientException.class})
  public ResponseEntity<ApiResponse.Result<Object>> globalClientException(ApiClientException ace) {
    return new ResponseEntity<>(ace.body(), ace.status());
  }

  @ExceptionHandler({ApiServerException.class})
  public ResponseEntity<ApiResponse.Result<Object>> globalServerException(ApiServerException ase) {
    return new ResponseEntity<>(ase.body(), ase.status());
  }

  @ExceptionHandler(Exception400.class)
  public ResponseEntity<ApiResponse.Result<Object>> badRequest(Exception400 e) {
    return new ResponseEntity<>(e.body(), e.status());
  }

  @ExceptionHandler(Exception401.class)
  public ResponseEntity<ApiResponse.Result<Object>> unAuthorized(Exception401 e) {
    return new ResponseEntity<>(e.body(), e.status());
  }

  @ExceptionHandler(Exception403.class)
  public ResponseEntity<ApiResponse.Result<Object>> forbidden(Exception403 e) {
    return new ResponseEntity<>(e.body(), e.status());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse.Result<Object>> unknownServerError(Exception e) {
    ApiResponse.Result<Object> apiResult =
        ApiResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
