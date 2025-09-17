package org.kashcode.todoapp.dtos.requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TodoUpdateRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Boolean completed;
}
