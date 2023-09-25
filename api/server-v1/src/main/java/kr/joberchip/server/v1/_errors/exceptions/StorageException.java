package kr.joberchip.server.v1._errors.exceptions;

public class StorageException extends ApiServerException {
  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
