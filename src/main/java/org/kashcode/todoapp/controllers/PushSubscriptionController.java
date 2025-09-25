package org.kashcode.todoapp.controllers;

import org.kashcode.todoapp.dtos.requests.PushSubscriptionRequest;
import org.kashcode.todoapp.dtos.responses.PushSubscriptionResponse;
import org.kashcode.todoapp.services.PushSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/push-subscriptions")
public class PushSubscriptionController {

    private final PushSubscriptionService pushSubscriptionService;

    public PushSubscriptionController(PushSubscriptionService pushSubscriptionService) {
        this.pushSubscriptionService = pushSubscriptionService;
    }


    @PostMapping("/{userId}/subscribe")
    public ResponseEntity<PushSubscriptionResponse> subscribe(
            @PathVariable Long userId,
            @RequestBody PushSubscriptionRequest request
    ) {
        PushSubscriptionResponse response = pushSubscriptionService.subscribe(userId, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{userId}/unsubscribe")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long userId,
            @RequestParam String endpoint
    ) {
        pushSubscriptionService.unsubscribe(userId, endpoint);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<PushSubscriptionResponse>> getSubscriptions(
            @PathVariable Long userId
    ) {
        List<PushSubscriptionResponse> responses = pushSubscriptionService.getSubscriptions(userId);
        return ResponseEntity.ok(responses);
    }


    @PostMapping("/{userId}/send-push")
    public ResponseEntity<Void> sendWebPush(
            @PathVariable Long userId,
            @RequestParam String message
    ) {
        pushSubscriptionService.sendWebPush(userId, message);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{userId}/send-email")
    public ResponseEntity<Void> sendEmail(
            @PathVariable Long userId,
            @RequestParam String subject,
            @RequestParam String message
    ) {
        pushSubscriptionService.sendEmail(userId, subject, message);
        return ResponseEntity.ok().build();
    }
}
