package org.kashcode.todoapp.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encodePassword(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    public static boolean verify(String rawPassword, String hashedPassword) {
        return ENCODER.matches(rawPassword, hashedPassword);
    }
}
