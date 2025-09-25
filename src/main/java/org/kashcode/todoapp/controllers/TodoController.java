package org.kashcode.todoapp.controllers;

import org.kashcode.todoapp.dtos.requests.TodoRequest;
import org.kashcode.todoapp.dtos.requests.TodoUpdateRequest;
import org.kashcode.todoapp.dtos.responses.TodoResponse;
import org.kashcode.todoapp.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping("/{userId}")
    public ResponseEntity<TodoResponse> createTodo(
            @PathVariable Long userId,
            @Valid @RequestBody TodoRequest request
    ) {
        TodoResponse response = todoService.createTodo(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoUpdateRequest request
    ) {
        TodoResponse response = todoService.updateTodo(id, request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoResponse>> getTodosByUser(@PathVariable Long userId) {
        List<TodoResponse> responses = todoService.getTodosByUser(userId);
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        TodoResponse response = todoService.getTodoById(id);
        return ResponseEntity.ok(response);
    }
}
