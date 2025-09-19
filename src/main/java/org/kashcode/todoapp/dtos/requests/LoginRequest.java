package org.kashcode.todoapp.dtos.requests;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email(message = "Email is not valid")
    private String usernameOrEmail;
    private String password;
}
