package org.kashcode.todoapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushSubscriptionResponse {
    private Long id;
    private String endpoint;
    private String publicKey;
    private String authSecret;
    private Long userId;
}
