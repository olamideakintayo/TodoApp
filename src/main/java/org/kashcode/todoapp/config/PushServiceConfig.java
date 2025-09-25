package org.kashcode.todoapp.config;

import nl.martijndwars.webpush.PushService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;

@Configuration
public class PushServiceConfig {

    @Bean
    public PushService pushService(
            @Value("${vapid.public.key}") String publicKey,
            @Value("${vapid.private.key}") String privateKey
    ) throws GeneralSecurityException {
        return new PushService(publicKey, privateKey);
    }
}
