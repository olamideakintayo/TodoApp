package org.kashcode.todoapp.exceptions;

public class PushSubscriptionNotFoundException extends RuntimeException {
    public PushSubscriptionNotFoundException(String message) {
        super(message);
    }
}
