package org.kashcode.todoapp.services;

import org.kashcode.todoapp.data.models.Reminder;
import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.data.repositories.ReminderRepository;
import org.kashcode.todoapp.data.repositories.TodoRepository;
import org.kashcode.todoapp.dtos.requests.ReminderRequest;
import org.kashcode.todoapp.dtos.responses.ReminderResponse;
import org.kashcode.todoapp.exceptions.DuplicateReminderException;
import org.kashcode.todoapp.exceptions.ReminderAlreadyTriggeredException;
import org.kashcode.todoapp.exceptions.ReminderNotFoundException;
import org.kashcode.todoapp.exceptions.TodoNotFoundException;
import org.kashcode.todoapp.utils.ReminderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final TodoRepository todoRepository;
    private final PushSubscriptionService pushSubscriptionService;

    public ReminderServiceImpl(ReminderRepository reminderRepository,
                               TodoRepository todoRepository,
                               PushSubscriptionService pushSubscriptionService) {
        this.reminderRepository = reminderRepository;
        this.todoRepository = todoRepository;
        this.pushSubscriptionService = pushSubscriptionService;
    }

    @Override
    public ReminderResponse createReminder(Long todoId, ReminderRequest request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));


        List<Reminder> existingReminders = reminderRepository.findByTodo(todo);


        boolean duplicate = existingReminders.stream()
                .anyMatch(r ->
                        Math.abs(java.time.Duration.between(r.getRemindAt(), request.getRemindAt()).toSeconds()) < 5
                );

        if (duplicate) {
            throw new DuplicateReminderException("A reminder already exists for this Todo at a similar time.");
        }

        Reminder reminder = ReminderMapper.toEntity(request);
        reminder.setTodo(todo);

        Reminder saved = reminderRepository.save(reminder);
        return ReminderMapper.toResponse(saved);
    }

    @Override
    public ReminderResponse updateReminder(Long id, ReminderRequest request) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));

        if (reminder.isTriggered()) {
            throw new ReminderAlreadyTriggeredException("Cannot update a reminder that has already been triggered.");
        }

        reminder.setRemindAt(request.getRemindAt());
        reminder.setType(request.getType());

        Reminder updated = reminderRepository.save(reminder);
        return ReminderMapper.toResponse(updated);
    }

    @Override
    public void deleteReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));
        reminderRepository.delete(reminder);
    }

    @Override
    public List<ReminderResponse> getRemindersByTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));

        return reminderRepository.findByTodo(todo).stream()
                .map(ReminderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReminderResponse getReminderById(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));
        return ReminderMapper.toResponse(reminder);
    }

    @Override
    public ReminderResponse triggerReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));

        if (reminder.isTriggered()) {
            throw new ReminderAlreadyTriggeredException("Reminder already triggered.");
        }

        reminder.setTriggered(true);
        Reminder updated = reminderRepository.save(reminder);

        Long userId = updated.getTodo().getUser().getId();
        String todoTitle = updated.getTodo().getTitle();

        String notificationMessage = "⏰ Reminder: " + todoTitle +
                " (Scheduled at: " + updated.getRemindAt() + ")";

        pushSubscriptionService.sendWebPush(userId, notificationMessage);


        pushSubscriptionService.sendEmail(userId, "Reminder Notification", notificationMessage);

        return ReminderMapper.toResponse(updated);
    }
}
