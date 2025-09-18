package org.kashcode.todoapp.services;

import org.kashcode.todoapp.dtos.requests.ReminderRequest;
import org.kashcode.todoapp.dtos.responses.ReminderResponse;

import java.util.List;

public interface ReminderService {
    ReminderResponse createReminder(Long todoId, ReminderRequest request);
    ReminderResponse updateReminder(Long id, ReminderRequest request);
    void deleteReminder(Long id);
    List<ReminderResponse> getRemindersByTodo(Long todoId);
    ReminderResponse getReminderById(Long id);
    ReminderResponse triggerReminder(Long id);
}
