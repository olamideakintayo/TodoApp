package org.kashcode.todoapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReminderResponse {
    private Long id;
    private LocalDateTime remindAt;
    private String note;
    private Long todoId;
}
