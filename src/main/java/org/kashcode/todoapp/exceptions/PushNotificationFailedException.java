package org.kashcode.todoapp.exceptions;

public class PushNotificationFailedException extends RuntimeException {
    public PushNotificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
