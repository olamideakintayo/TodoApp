package org.kashcode.todoapp.exceptions;

public class ReminerNotFoundException extends RuntimeException {
    public ReminerNotFoundException(String message) {
        super(message);
    }
}
