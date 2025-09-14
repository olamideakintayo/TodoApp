package org.kashcode.todoapp.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid username/email or password");
    }
}
