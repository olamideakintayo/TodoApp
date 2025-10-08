package org.kashcode.todoapp.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.kashcode.todoapp.data.models.Reminder;
import org.kashcode.todoapp.data.repositories.ReminderRepository;
import org.kashcode.todoapp.services.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ReminderScheduler {

    private final ReminderRepository reminderRepository;
    private final ReminderService reminderService;

    public ReminderScheduler(ReminderRepository reminderRepository, ReminderService reminderService) {
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
    }


    @Scheduled(fixedRate = 60_000)
    public void processDueReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> dueReminders = reminderRepository
                .findByTriggeredFalseAndRemindAtBefore(now);

        if (dueReminders.isEmpty()) return;

        log.info("⏰ Found {} due reminders at {}", dueReminders.size(), now);

        for (Reminder reminder : dueReminders) {
            try {
                reminderService.triggerReminder(reminder.getId());
                log.info("Triggered reminder #{} for todo '{}'", reminder.getId(), reminder.getTodo().getTitle());
            } catch (Exception e) {
                log.error("Failed to trigger reminder #{}: {}", reminder.getId(), e.getMessage(), e);
            }
        }
    }
}
