package org.kashcode.todoapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kashcode.todoapp.data.models.ReminderType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReminderResponse {
    private Long id;
    private LocalDateTime remindAt;
    private ReminderType type;
    private Long todoId;
}
