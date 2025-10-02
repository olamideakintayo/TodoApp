package org.kashcode.todoapp.controllers;

import org.kashcode.todoapp.dtos.requests.LoginRequest;
import org.kashcode.todoapp.dtos.requests.UserRequest;
import org.kashcode.todoapp.dtos.responses.LoginResponse;
import org.kashcode.todoapp.dtos.responses.UserResponse;
import org.kashcode.todoapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
