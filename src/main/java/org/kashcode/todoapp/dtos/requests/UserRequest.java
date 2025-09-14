package org.kashcode.todoapp.dtos.requests;


import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
}
