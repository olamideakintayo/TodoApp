package org.kashcode.todoapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    private static final String SECRET = "supersecretkeysupersecretkey12345";
    private static final long EXPIRATION_TIME = 1000 * 60;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl(SECRET, EXPIRATION_TIME);
    }

    @Test
    void shouldGenerateTokenAndExtractUsername() {
        String token = jwtService.generateToken("olamide");

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String username = jwtService.extractUsername(token);
        assertEquals("olamide", username);
    }

    @Test
    void shouldValidateValidToken() {
        String token = jwtService.generateToken("olamide");

        assertTrue(jwtService.validateToken(token, "olamide"));
    }

    @Test
    void shouldFailValidationForWrongUsername() {
        String token = jwtService.generateToken("olamide");

        assertFalse(jwtService.validateToken(token, "wrongUser"));
    }

    @Test
    void shouldFailValidationForExpiredToken() throws InterruptedException {
        JwtServiceImpl shortLivedJwt = new JwtServiceImpl(SECRET, 500);
        String token = shortLivedJwt.generateToken("olamide");

        Thread.sleep(1000);

        assertFalse(shortLivedJwt.validateToken(token, "olamide"));
    }

    @Test
    void shouldHandleBearerPrefixInToken() {
        String token = jwtService.generateToken("olamide");
        String bearerToken = "Bearer " + token;

        assertTrue(jwtService.validateToken(bearerToken, "olamide"));
        assertEquals("olamide", jwtService.extractUsername(bearerToken));
    }

    @Test
    void shouldReturnFalseForMalformedToken() {
        assertFalse(jwtService.validateToken("invalid.token", "olamide"));
    }

    @Test
    void shouldReturnEmptyStringWhenExtractingFromNullToken() {
        assertThrows(Exception.class, () -> jwtService.extractUsername(null));
    }
}
