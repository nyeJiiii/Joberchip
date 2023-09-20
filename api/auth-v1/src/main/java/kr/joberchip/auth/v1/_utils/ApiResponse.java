package kr.joberchip.auth.v1._utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse {

  public static <T> Result<T> success() {
    return Result.of(HttpStatus.NO_CONTENT.value(), true, null, null);
  }

  public static <T> Result<T> success(T response) {
    return Result.of(HttpStatus.OK.value(), true, response, null);
  }

  public static Result<Object> error(String message, HttpStatus status) {
    return Result.of(status.value(), false, null, Error.of(message));
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public record Result<T>(int status, boolean success, T response, Error error) {
    public static <T> Result<T> of(int status, boolean success, T response, Error error) {
      return new Result<T>(status, success, response, error);
    }
  }

  public record Error(String message) {
    public static Error of(String message) {
      return new Error(message);
    }
  }
}
