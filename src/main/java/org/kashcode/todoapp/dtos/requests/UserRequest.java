package org.kashcode.todoapp.dtos.requests;


import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    @Email(message = "Email is not valid")
    private String email;
    private String password;
}
