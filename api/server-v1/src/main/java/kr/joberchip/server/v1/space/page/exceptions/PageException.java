package kr.joberchip.server.v1.space.page.exceptions;

public class PageException extends RuntimeException {
  public PageException(String message) {
    super(message);
  }

  public PageException(String message, Throwable cause) {
    super(message, cause);
  }
}
