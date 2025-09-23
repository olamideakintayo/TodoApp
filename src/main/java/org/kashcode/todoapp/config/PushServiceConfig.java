package org.kashcode.todoapp.config;

import nl.martijndwars.webpush.PushService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;

@Configuration
public class PushServiceConfig {

    @Bean
    public PushService pushService(
            @Value("${VAPID_PUBLIC_KEY}") String publicKey,
            @Value("${VAPID_PRIVATE_KEY}") String privateKey
    ) throws GeneralSecurityException {
        return new PushService(publicKey, privateKey);
    }
}
