package org.kashcode.todoapp.utils;

import org.kashcode.todoapp.data.models.Reminder;
import org.kashcode.todoapp.dtos.requests.ReminderRequest;
import org.kashcode.todoapp.dtos.responses.ReminderResponse;

public class ReminderMapper {

    public static Reminder toEntity(ReminderRequest request) {
        Reminder reminder = new Reminder();
        reminder.setRemindAt(request.getRemindAt());
        reminder.setType(request.getType());
        return reminder;
    }

    public static ReminderResponse toResponse(Reminder reminder) {
        return new ReminderResponse(
                reminder.getId(),
                reminder.getRemindAt(),
                reminder.getType(),
                reminder.getTodo() != null ? reminder.getTodo().getId() : null
        );
    }
}
