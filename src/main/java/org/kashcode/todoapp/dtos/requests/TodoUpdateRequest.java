package org.kashcode.todoapp.dtos.requests;

import lombok.Data;

@Data
public class TodoUpdateRequest {
    private String title;
    private String description;
    private Boolean completed;
}
