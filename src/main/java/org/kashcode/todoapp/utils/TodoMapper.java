package org.kashcode.todoapp.utils;

import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.dtos.requests.TodoRequest;
import org.kashcode.todoapp.dtos.requests.TodoUpdateRequest;
import org.kashcode.todoapp.dtos.responses.TodoResponse;

import java.time.LocalDateTime;

public class TodoMapper {

    public static Todo toEntity(TodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(false);
        todo.setDateAdded(LocalDateTime.now());
        todo.setDueDate(request.getDueDate());
        return todo;
    }

    public static void updateEntity(Todo todo, TodoUpdateRequest request) {
        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }
        if (request.getDueDate() != null) {
            todo.setDueDate(request.getDueDate());
        }
    }

    public static TodoResponse toResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setUserId(todo.getUser().getId());
        response.setDateAdded(todo.getDateAdded());
        response.setDueDate(todo.getDueDate());
        return response;
    }
}
