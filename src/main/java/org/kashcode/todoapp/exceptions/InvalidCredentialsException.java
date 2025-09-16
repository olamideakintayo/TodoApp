package org.kashcode.todoapp.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String invalidUsernameOrPassword) {
        super("Invalid username/email or password");
    }
}
