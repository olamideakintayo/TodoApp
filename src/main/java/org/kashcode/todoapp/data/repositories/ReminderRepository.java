package org.kashcode.todoapp.data.repositories;

import org.kashcode.todoapp.data.models.Reminder;
import org.kashcode.todoapp.data.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {


    List<Reminder> findByTodo(Todo todo);

    List<Reminder> findByTriggeredFalseAndRemindAtBefore(LocalDateTime now);


    boolean existsByTodoAndRemindAt(Todo todo, LocalDateTime remindAt);
}
