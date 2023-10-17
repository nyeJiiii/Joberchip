package kr.joberchip.server.v1.global.errors.exceptions;

public class DuplicatedUsernameException extends RuntimeException{
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
