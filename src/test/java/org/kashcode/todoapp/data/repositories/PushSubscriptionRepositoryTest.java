package org.kashcode.todoapp.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PushSubscriptionRepositoryTest {

    @Autowired
    private PushSubscriptionRepository pushSubscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    public User createValidUser() {
        User user = new User();
        user.setUsername("olamide");
        user.setEmail("divineolamide977@gmail.com");
        user.setPassword("password");
        return userRepository.save(user);
    }

    public User createValidUser2() {
        User user2 = new User();
        user2.setUsername("Divine");
        user2.setEmail("olamide977@gmail.com");
        user2.setPassword("pass");
        return userRepository.save(user2);
    }




    @BeforeEach
    void cleanDatabase() {
        pushSubscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testSavePushSubscription() {
        PushSubscription subscription = new PushSubscription();
        User user = createValidUser();
        subscription.setEndpoint("https://example.com/endpoint");
        subscription.setPublicKey("p256dhKey");
        subscription.setAuthSecret("authKey");
        subscription.setUser(user);

        PushSubscription saved = pushSubscriptionRepository.save(subscription);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEndpoint()).isEqualTo("https://example.com/endpoint");
    }

    @Test
    void testFindByEndpoint() {
        PushSubscription subscription1 = new PushSubscription();
        User user1 = createValidUser();
        subscription1.setEndpoint("https://example.com/endpoint");
        subscription1.setPublicKey("p256dhKey");
        subscription1.setAuthSecret("authKey");
        subscription1.setUser(user1);
    }

    @Test
    void testFindAllPushSubscriptions() {
        PushSubscription subscription1 = new PushSubscription();
        User user1 = createValidUser();
        subscription1.setUser(user1);
        subscription1.setEndpoint("https://example.com/endpoint");
        subscription1.setPublicKey("p256dhKey");
        subscription1.setAuthSecret("authKey");

        PushSubscription subscription2 = new PushSubscription();
        User user2 = createValidUser2();
        subscription2.setUser(user2);
        subscription2.setEndpoint("https://example.com/endpoint2");
        subscription2.setPublicKey("p256dhKey2");
        subscription2.setAuthSecret("authKey2");

        pushSubscriptionRepository.save(subscription1);
        pushSubscriptionRepository.save(subscription2);

        List<PushSubscription> subscriptions = pushSubscriptionRepository.findAll();

        assertThat(subscriptions).hasSize(2);
    }

    @Test
    void testDeletePushSubscription() {
        PushSubscription subscription = new PushSubscription();
        User user = createValidUser();
        subscription.setEndpoint("https://example.com/endpoint");
        subscription.setPublicKey("p256dhKey");
        subscription.setAuthSecret("authKey");
        subscription.setUser(user);

        pushSubscriptionRepository.save(subscription);
        pushSubscriptionRepository.delete(subscription);
    }
}