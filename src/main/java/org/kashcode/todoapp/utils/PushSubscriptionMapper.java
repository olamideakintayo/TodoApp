package org.kashcode.todoapp.utils;

import org.kashcode.todoapp.data.models.PushSubscription;
import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.dtos.requests.PushSubscriptionRequest;
import org.kashcode.todoapp.dtos.responses.PushSubscriptionResponse;

public class PushSubscriptionMapper {

    public static PushSubscription toEntity(PushSubscriptionRequest request, User user) {
        PushSubscription subscription = new PushSubscription();
        subscription.setEndpoint(request.getEndpoint());
        subscription.setPublicKey(request.getPublicKey());
        subscription.setAuthSecret(request.getAuthSecret());
        subscription.setUser(user);
        return subscription;
    }

    public static PushSubscriptionResponse toResponse(PushSubscription subscription) {
        return new PushSubscriptionResponse(
                subscription.getId(),
                subscription.getEndpoint(),
                subscription.getPublicKey(),
                subscription.getAuthSecret(),
                subscription.getUser().getId()
        );
    }
}
