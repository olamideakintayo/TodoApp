package org.kashcode.todoapp.exceptions;

public class ReminderAlreadyTriggeredException extends RuntimeException {
    public ReminderAlreadyTriggeredException(String message) {
        super(message);
    }
}
