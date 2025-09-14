package org.kashcode.todoapp.dtos.requests;


import lombok.Data;

@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
