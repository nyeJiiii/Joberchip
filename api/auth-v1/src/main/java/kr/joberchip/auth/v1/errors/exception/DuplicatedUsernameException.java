package kr.joberchip.auth.v1.errors.exception;

public class DuplicatedUsernameException extends RuntimeException{
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
