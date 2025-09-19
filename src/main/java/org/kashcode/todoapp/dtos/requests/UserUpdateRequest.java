package org.kashcode.todoapp.dtos.requests;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    @Email(message = "Email is not valid")
    private String email;
    private String password;
}

