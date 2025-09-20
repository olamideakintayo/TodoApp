package org.kashcode.todoapp.dtos.requests;

import lombok.Data;

@Data
public class PushSubscriptionRequest {
    private String endpoint;
    private String publicKey;
    private String authSecret;
}
