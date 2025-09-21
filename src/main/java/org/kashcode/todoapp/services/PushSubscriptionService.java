package org.kashcode.todoapp.services;

import org.kashcode.todoapp.dtos.requests.PushSubscriptionRequest;
import org.kashcode.todoapp.dtos.responses.PushSubscriptionResponse;

import java.util.List;

public interface PushSubscriptionService {
    PushSubscriptionResponse subscribe(Long userId, PushSubscriptionRequest request);
    void unsubscribe(Long userId, String endpoint);
    List<PushSubscriptionResponse> getSubscriptions(Long userId);
    void sendWebPush(Long userId, String message);
    void sendEmail(Long userId, String subject, String message);
}
