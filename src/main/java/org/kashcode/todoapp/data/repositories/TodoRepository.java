package org.kashcode.todoapp.data.repositories;

import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUser(User user);

    List<Todo> findByUserAndCompleted(User user, boolean completed);
}
