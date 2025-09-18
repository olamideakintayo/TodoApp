package org.kashcode.todoapp.dtos.requests;

import lombok.Data;
import org.kashcode.todoapp.data.models.ReminderType;

import java.time.LocalDateTime;

@Data
public class ReminderRequest {
    private LocalDateTime remindAt;
    private ReminderType type;
}
