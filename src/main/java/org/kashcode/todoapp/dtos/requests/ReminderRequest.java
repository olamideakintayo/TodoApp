package org.kashcode.todoapp.dtos.requests;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderRequest {
    private LocalDateTime remindAt;
    private String note;
}
