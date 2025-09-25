package org.kashcode.todoapp.controllers;

import org.kashcode.todoapp.dtos.requests.ReminderRequest;
import org.kashcode.todoapp.dtos.responses.ReminderResponse;
import org.kashcode.todoapp.services.ReminderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/{todoId}")
    public ResponseEntity<ReminderResponse> createReminder(
            @PathVariable Long todoId,
            @Valid @RequestBody ReminderRequest request
    ) {
        ReminderResponse response = reminderService.createReminder(todoId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(
            @PathVariable Long id,
            @Valid @RequestBody ReminderRequest request
    ) {
        ReminderResponse response = reminderService.updateReminder(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<List<ReminderResponse>> getRemindersByTodo(@PathVariable Long todoId) {
        List<ReminderResponse> responses = reminderService.getRemindersByTodo(todoId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponse> getReminderById(@PathVariable Long id) {
        ReminderResponse response = reminderService.getReminderById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/trigger")
    public ResponseEntity<ReminderResponse> triggerReminder(@PathVariable Long id) {
        ReminderResponse response = reminderService.triggerReminder(id);
        return ResponseEntity.ok(response);
    }
}
