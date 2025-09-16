package org.kashcode.todoapp.dtos.responses;

import lombok.Data;

@Data
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private Long userId;
}
