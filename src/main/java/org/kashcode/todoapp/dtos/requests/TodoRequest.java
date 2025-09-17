package org.kashcode.todoapp.dtos.requests;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TodoRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private LocalDateTime dueDate;
}
