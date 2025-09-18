package org.kashcode.todoapp.exceptions;

public class DuplicateReminderException extends RuntimeException {
    public DuplicateReminderException(String message) {
        super(message);
    }
}
