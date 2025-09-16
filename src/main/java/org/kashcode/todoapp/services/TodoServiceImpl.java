package org.kashcode.todoapp.services;

import org.kashcode.todoapp.data.models.Todo;
import org.kashcode.todoapp.data.models.User;
import org.kashcode.todoapp.data.repositories.TodoRepository;
import org.kashcode.todoapp.data.repositories.UserRepository;
import org.kashcode.todoapp.dtos.requests.TodoRequest;
import org.kashcode.todoapp.dtos.requests.TodoUpdateRequest;
import org.kashcode.todoapp.dtos.responses.TodoResponse;
import org.kashcode.todoapp.exceptions.TodoNotFoundException;
import org.kashcode.todoapp.exceptions.UserNotFoundException;
import org.kashcode.todoapp.utils.TodoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TodoResponse createTodo(Long userId, TodoRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Todo todo = TodoMapper.toEntity(request);
        todo.setUser(user);

        Todo saved = todoRepository.save(todo);
        return TodoMapper.toResponse(saved);
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoUpdateRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        TodoMapper.updateEntity(todo, request);
        Todo updated = todoRepository.save(todo);
        return TodoMapper.toResponse(updated);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        todoRepository.delete(todo);
    }

    @Override
    public List<TodoResponse> getTodosByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return todoRepository.findByUser(user).stream()
                .map(TodoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        return TodoMapper.toResponse(todo);
    }
}
