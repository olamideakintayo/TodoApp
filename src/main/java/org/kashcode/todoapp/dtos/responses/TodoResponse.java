package org.kashcode.todoapp.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateAdded;
    private LocalDateTime dueDate;
    private boolean completed;
    private Long userId;
}
