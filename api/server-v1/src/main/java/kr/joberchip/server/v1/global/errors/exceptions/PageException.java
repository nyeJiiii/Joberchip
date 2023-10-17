package kr.joberchip.server.v1.global.errors.exceptions;

public class PageException extends ApiServerException {
  public PageException(String message) {
    super(message);
  }

  public PageException(String message, Throwable cause) {
    super(message, cause);
  }
}
