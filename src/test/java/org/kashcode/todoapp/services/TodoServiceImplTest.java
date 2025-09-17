package org.kashcode.todoapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.data.repositories.TodoRepository;
import org.kashcode.todoapp.data.repositories.UserRepository;
import org.kashcode.todoapp.dtos.requests.TodoRequest;
import org.kashcode.todoapp.dtos.requests.TodoUpdateRequest;
import org.kashcode.todoapp.dtos.responses.TodoResponse;
import org.kashcode.todoapp.exceptions.TodoNotFoundException;
import org.kashcode.todoapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TodoServiceImplTest {

    @Autowired
    private TodoServiceImpl todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private static final LocalDateTime FIXED_DUE_DATE =
            LocalDateTime.of(2025, 10, 10, 14, 30);

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
        userRepository.flush();

        user = new User();
        user.setUsername("olamide");
        user.setEmail("olamide@gmail.com");
        user.setPassword("1234567890");
        user = userRepository.save(user);
    }

    private TodoRequest buildTodoRequest(String title, String description) {
        TodoRequest request = new TodoRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setDueDate(FIXED_DUE_DATE);
        return request;
    }

    @Test
    void shouldCreateTodoWithDueDate() {
        TodoResponse response = todoService.createTodo(user.getId(),
                buildTodoRequest("Finish project", "Complete the Spring Boot Todo App"));

        assertNotNull(response.getId());
        assertEquals("Finish project", response.getTitle());
        assertEquals("Complete the Spring Boot Todo App", response.getDescription());
        assertEquals(FIXED_DUE_DATE, response.getDueDate());
        assertFalse(response.isCompleted());
        assertNotNull(response.getDateAdded());
    }

    @Test
    void shouldFailToCreateTodoWhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class,
                () -> todoService.createTodo(999L,
                        buildTodoRequest("Invalid", "No user should exist")));
    }

    @Test
    void shouldUpdateTodoWhenIdIsValid() {
        TodoResponse created = todoService.createTodo(user.getId(),
                buildTodoRequest("Old Title", "Old Description"));

        TodoUpdateRequest updateRequest = new TodoUpdateRequest();
        updateRequest.setTitle("New Title");
        updateRequest.setDescription("New Description");
        updateRequest.setCompleted(true);
        updateRequest.setDueDate(FIXED_DUE_DATE.plusDays(5));

        TodoResponse updated = todoService.updateTodo(created.getId(), updateRequest);

        assertEquals("New Title", updated.getTitle());
        assertEquals("New Description", updated.getDescription());
        assertTrue(updated.isCompleted());
        assertEquals(FIXED_DUE_DATE.plusDays(5), updated.getDueDate());
    }

    @Test
    void shouldFailToUpdateTodoWhenIdIsInvalid() {
        TodoUpdateRequest updateRequest = new TodoUpdateRequest();
        updateRequest.setTitle("New Title");

        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateTodo(999L, updateRequest));
    }

    @Test
    void shouldDeleteTodoWhenIdIsValid() {
        TodoResponse created = todoService.createTodo(user.getId(),
                buildTodoRequest("To be deleted", "Delete me"));

        todoService.deleteTodo(created.getId());

        assertFalse(todoRepository.findById(created.getId()).isPresent());
    }

    @Test
    void shouldFailToDeleteTodoWhenIdIsInvalid() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.deleteTodo(999L));
    }

    @Test
    void shouldGetTodosByUserWhenUserExists() {
        todoService.createTodo(user.getId(), buildTodoRequest("Task 1", "Description 1"));
        todoService.createTodo(user.getId(), buildTodoRequest("Task 2", "Description 2"));

        List<TodoResponse> todos = todoService.getTodosByUser(user.getId());

        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(t -> t.getTitle().equals("Task 1")));
        assertTrue(todos.stream().anyMatch(t -> t.getTitle().equals("Task 2")));
    }

    @Test
    void shouldFailToGetTodosByUserWhenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class,
                () -> todoService.getTodosByUser(999L));
    }

    @Test
    void shouldGetTodoByIdWhenIdIsValid() {
        TodoResponse created = todoService.createTodo(user.getId(),
                buildTodoRequest("Find me", "I should be found"));

        TodoResponse found = todoService.getTodoById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Find me", found.getTitle());
        assertEquals(FIXED_DUE_DATE, found.getDueDate());
    }

    @Test
    void shouldFailToGetTodoByIdWhenIdIsInvalid() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.getTodoById(999L));
    }
}
