package org.kashcode.todoapp.services;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.kashcode.todoapp.data.models.PushSubscription;
import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.data.repositories.PushSubscriptionRepository;
import org.kashcode.todoapp.data.repositories.UserRepository;
import org.kashcode.todoapp.dtos.requests.PushSubscriptionRequest;
import org.kashcode.todoapp.dtos.responses.PushSubscriptionResponse;
import org.kashcode.todoapp.exceptions.*;
import org.kashcode.todoapp.utils.PushSubscriptionMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PushSubscriptionServiceImpl implements PushSubscriptionService {

    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PushService pushService;

    public PushSubscriptionServiceImpl(
            PushSubscriptionRepository pushSubscriptionRepository,
            UserRepository userRepository,
            JavaMailSender mailSender,
            PushService pushService
    ) {
        this.pushSubscriptionRepository = pushSubscriptionRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.pushService = pushService;


        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @Override
    public PushSubscriptionResponse subscribe(Long userId, PushSubscriptionRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        PushSubscription subscription = PushSubscriptionMapper.toEntity(request, user);
        pushSubscriptionRepository.save(subscription);

        return PushSubscriptionMapper.toResponse(subscription);
    }

    @Override
    public void unsubscribe(Long userId, String endpoint) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        pushSubscriptionRepository.deleteByUserAndEndpoint(user, endpoint);
    }

    @Override
    public List<PushSubscriptionResponse> getSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return pushSubscriptionRepository.findByUser(user).stream()
                .map(PushSubscriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void sendWebPush(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUser(user);

        if (subscriptions.isEmpty()) {
            throw new PushSubscriptionNotFoundException("No push subscriptions found for user: " + userId);
        }

        for (PushSubscription subscription : subscriptions) {
            try {
                Notification notification = new Notification(
                        subscription.getEndpoint(),
                        subscription.getPublicKey(),
                        subscription.getAuthSecret(),
                        message.getBytes()
                );
                pushService.send(notification);
            } catch (GeneralSecurityException | java.io.IOException e) {
                throw new PushNotificationFailedException(
                        "Failed to send push notification to endpoint: " + subscription.getEndpoint(), e
                );
            }
        }
    }

    @Override
    public void sendEmail(Long userId, String subject, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("User does not have a valid email address");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
