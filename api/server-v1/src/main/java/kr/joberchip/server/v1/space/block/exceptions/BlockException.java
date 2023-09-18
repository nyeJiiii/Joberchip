package kr.joberchip.server.v1.space.block.exceptions;

public class BlockException extends RuntimeException {
  public BlockException(String message) {
    super(message);
  }

  public BlockException(String message, Throwable cause) {
    super(message, cause);
  }
}
