package org.kashcode.todoapp.exceptions;

public class ReminderNotFoundException extends RuntimeException {
    public ReminderNotFoundException(String message) {
        super(message);
    }
}


