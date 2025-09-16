package org.kashcode.todoapp.services;

import org.kashcode.todoapp.dtos.responses.*;
import org.kashcode.todoapp.dtos.requests.*;

import java.util.List;

public interface UserService {
    UserResponse register(UserRequest request);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse login(LoginRequest request);
}
