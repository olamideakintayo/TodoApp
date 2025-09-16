package org.kashcode.todoapp.services;

import org.kashcode.todoapp.dtos.requests.TodoRequest;
import org.kashcode.todoapp.dtos.requests.TodoUpdateRequest;
import org.kashcode.todoapp.dtos.responses.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(Long userId, TodoRequest request);
    TodoResponse updateTodo(Long id, TodoUpdateRequest request);
    void deleteTodo(Long id);
    List<TodoResponse> getTodosByUser(Long userId);
    TodoResponse getTodoById(Long id);
}
