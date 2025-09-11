package org.kashcode.todoapp.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.Reminder;
import org.kashcode.todoapp.data.models.ReminderType;
import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.data.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ReminderRepositoryTest {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User createValidUser() {
        User user = new User();
        user.setUsername("olamide");
        user.setEmail("olamide@example.com");
        user.setPassword("secret");
        return userRepository.save(user);
    }

    private Todo createValidTodo(User user) {
        Todo todo = new Todo();
        todo.setTitle("Finish assignment");
        todo.setDescription("Complete Spring Boot homework");
        todo.setDateAdded(LocalDateTime.now());
        todo.setDueDate(LocalDateTime.now().plusDays(1));
        todo.setCompleted(false);
        todo.setUser(user);
        return todoRepository.save(todo);
    }

    @BeforeEach
    void cleanDatabase() {
        reminderRepository.deleteAll();
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testSaveReminder() {
        User user = createValidUser();
        Todo todo = createValidTodo(user);

        Reminder reminder = new Reminder();
        reminder.setRemindAt(LocalDateTime.now().plusHours(2));
        reminder.setTriggered(false);
        reminder.setType(ReminderType.DESKTOP_NOTIFICATION);
        reminder.setTodo(todo);

        Reminder saved = reminderRepository.save(reminder);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTodo().getId()).isEqualTo(todo.getId());
        assertThat(saved.getType()).isEqualTo(ReminderType.DESKTOP_NOTIFICATION);
    }

    @Test
    void testFindAllReminders() {
        User user = createValidUser();
        Todo todo = createValidTodo(user);

        Reminder reminder1 = new Reminder();
        reminder1.setRemindAt(LocalDateTime.now().plusHours(3));
        reminder1.setTriggered(false);
        reminder1.setType(ReminderType.EMAIL);
        reminder1.setTodo(todo);

        Reminder reminder2 = new Reminder();
        reminder2.setRemindAt(LocalDateTime.now().plusDays(1));
        reminder2.setTriggered(false);
        reminder2.setType(ReminderType.EMAIL);
        reminder2.setTodo(todo);

        reminderRepository.save(reminder1);
        reminderRepository.save(reminder2);

        List<Reminder> reminders = reminderRepository.findAll();

        assertThat(reminders).hasSize(2);
        assertThat(reminders).extracting(Reminder::getType)
                .containsExactlyInAnyOrder(ReminderType.EMAIL, ReminderType.EMAIL);
    }

    @Test
    void testDeleteReminder() {
        User user = createValidUser();
        Todo todo = createValidTodo(user);

        Reminder reminder = new Reminder();
        reminder.setRemindAt(LocalDateTime.now().plusHours(5));
        reminder.setTriggered(false);
        reminder.setType(ReminderType.DESKTOP_NOTIFICATION);
        reminder.setTodo(todo);

        reminderRepository.save(reminder);
        reminderRepository.delete(reminder);

        assertThat(reminderRepository.findAll()).isEmpty();
    }

    @Test
    void testFindByRemindAtBeforeAndTriggeredFalse() {
        User user = createValidUser();
        Todo todo = createValidTodo(user);

        Reminder pastReminder = new Reminder();
        pastReminder.setRemindAt(LocalDateTime.now().minusHours(1));
        pastReminder.setTriggered(false);
        pastReminder.setType(ReminderType.EMAIL);
        pastReminder.setTodo(todo);

        Reminder futureReminder = new Reminder();
        futureReminder.setRemindAt(LocalDateTime.now().plusHours(5));
        futureReminder.setTriggered(false);
        futureReminder.setType(ReminderType.DESKTOP_NOTIFICATION);
        futureReminder.setTodo(todo);

        Reminder triggeredReminder = new Reminder();
        triggeredReminder.setRemindAt(LocalDateTime.now().minusHours(2));
        triggeredReminder.setTriggered(true);
        triggeredReminder.setType(ReminderType.DESKTOP_NOTIFICATION);
        triggeredReminder.setTodo(todo);

        reminderRepository.save(pastReminder);
        reminderRepository.save(futureReminder);
        reminderRepository.save(triggeredReminder);

        List<Reminder> results = reminderRepository
                .findByTriggeredFalseAndRemindAtBefore(LocalDateTime.now());

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getRemindAt()).isBefore(LocalDateTime.now());
        assertThat(results.get(0).isTriggered()).isFalse();
    }
}
