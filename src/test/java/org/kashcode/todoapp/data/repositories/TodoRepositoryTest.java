package org.kashcode.todoapp.data.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setUsername("olamide");
        user.setEmail("olamide@example.com");
        user.setPassword("secret");
        userRepository.save(user);
    }

    @Test
    void testSaveTodo() {
        Todo todo = new Todo();
        todo.setTitle("Finish project");
        todo.setDateAdded(LocalDateTime.now());
        todo.setUser(user);

        Todo saved = todoRepository.save(todo);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Finish project");
        assertThat(saved.getUser().getUsername()).isEqualTo("olamide");
    }

    @Test
    void testFindByUser() {
        Todo todo1 = new Todo();
        todo1.setTitle("Task 1");
        todo1.setDateAdded(LocalDateTime.now());
        todo1.setUser(user);

        Todo todo2 = new Todo();
        todo2.setTitle("Task 2");
        todo2.setDateAdded(LocalDateTime.now());
        todo2.setUser(user);

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        List<Todo> todos = todoRepository.findByUser(user);

        assertThat(todos).hasSize(2)
                .extracting(Todo::getTitle)
                .containsExactlyInAnyOrder("Task 1", "Task 2");
    }

    @Test
    void testFindByUserAndCompleted() {
        Todo completedTodo = new Todo();
        completedTodo.setTitle("Completed task");
        completedTodo.setDateAdded(LocalDateTime.now());
        completedTodo.setCompleted(true);
        completedTodo.setUser(user);

        Todo pendingTodo = new Todo();
        pendingTodo.setTitle("Pending task");
        pendingTodo.setDateAdded(LocalDateTime.now());
        pendingTodo.setCompleted(false);
        pendingTodo.setUser(user);

        todoRepository.save(completedTodo);
        todoRepository.save(pendingTodo);

        List<Todo> completed = todoRepository.findByUserAndCompleted(user, true);
        List<Todo> pending = todoRepository.findByUserAndCompleted(user, false);

        assertThat(completed).hasSize(1)
                .extracting(Todo::getTitle)
                .containsExactly("Completed task");

        assertThat(pending).hasSize(1)
                .extracting(Todo::getTitle)
                .containsExactly("Pending task");
    }

    @Test
    void testDeleteTodo() {
        Todo todo = new Todo();
        todo.setTitle("Delete me");
        todo.setDateAdded(LocalDateTime.now());
        todo.setUser(user);

        todoRepository.save(todo);
        todoRepository.delete(todo);

        assertThat(todoRepository.findByUser(user)).isEmpty();
    }
}
