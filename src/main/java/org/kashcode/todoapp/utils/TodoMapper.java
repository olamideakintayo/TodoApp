package org.kashcode.todoapp.utils;

import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.dtos.requests.TodoRequest;
import org.kashcode.todoapp.dtos.requests.TodoUpdateRequest;
import org.kashcode.todoapp.dtos.responses.TodoResponse;

public class TodoMapper {

    public static Todo toEntity(TodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(false);
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
    }

    public static TodoResponse toResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setUserId(todo.getUser().getId());
        return response;
    }
}
