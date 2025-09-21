package org.kashcode.todoapp.config;

import nl.martijndwars.webpush.PushService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;

@Configuration
public class PushServiceConfig {

    @Bean
    public PushService pushService() throws GeneralSecurityException {
        String publicKey = System.getenv("VAPID_PUBLIC_KEY");
        String privateKey = System.getenv("VAPID_PRIVATE_KEY");
        String subject = "mailto:admin@yourapp.com";

        return new PushService(publicKey, privateKey, subject);
    }
}
