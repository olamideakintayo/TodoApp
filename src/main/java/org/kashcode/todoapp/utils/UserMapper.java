package org.kashcode.todoapp.utils;

import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.dtos.responses.UserResponse;

public class UserMapper {
    private UserMapper() {}

    public static UserResponse toResponse(User user, String token) {
        if (user == null) return null;
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                token
        );
    }
}