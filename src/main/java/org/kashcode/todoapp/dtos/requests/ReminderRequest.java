package org.kashcode.todoapp.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.kashcode.todoapp.data.models.ReminderType;

import java.time.LocalDateTime;

@Data
public class ReminderRequest {
    @NotNull(message = "Reminder date is required")
    private LocalDateTime remindAt;
    @NotNull(message = "Reminder type is required")
    private ReminderType type;
}
