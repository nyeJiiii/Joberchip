package kr.joberchip.server.v1._errors.exceptions;

public class BlockException extends ApiServerException {
  public BlockException(String message) {
    super(message);
  }

  public BlockException(String message, Throwable cause) {
    super(message, cause);
  }
}
