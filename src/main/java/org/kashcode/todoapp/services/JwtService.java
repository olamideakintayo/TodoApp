package org.kashcode.todoapp.services;


public interface JwtService {
    String generateToken(String username);
    String extractUsername(String token);
    boolean validateToken(String token, String username);
}
