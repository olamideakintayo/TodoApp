package org.kashcode.todoapp.dtos.requests;


import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String email;
    private String password;
}

