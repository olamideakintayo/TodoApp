package org.kashcode.todoapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
    private Long userId;
    private String username;
    private String email;
}
