package org.kashcode.todoapp.dtos.requests;

import lombok.Data;

@Data
public class TodoRequest {
    private String title;
    private String description;
}
