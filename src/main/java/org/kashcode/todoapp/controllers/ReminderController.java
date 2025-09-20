package org.kashcode.todoapp.controllers;

import org.kashcode.todoapp.dtos.requests.ReminderRequest;
import org.kashcode.todoapp.dtos.responses.ReminderResponse;
import org.kashcode.todoapp.services.ReminderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/{todoId}")
    public ResponseEntity<ReminderResponse> createReminder(@PathVariable Long todoId,
                                                           @RequestBody ReminderRequest request) {
        return ResponseEntity.ok(reminderService.createReminder(todoId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(@PathVariable Long id,
                                                           @RequestBody ReminderRequest request) {
        return ResponseEntity.ok(reminderService.updateReminder(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<List<ReminderResponse>> getRemindersByTodo(@PathVariable Long todoId) {
        return ResponseEntity.ok(reminderService.getRemindersByTodo(todoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponse> getReminderById(@PathVariable Long id) {
        return ResponseEntity.ok(reminderService.getReminderById(id));
    }

    @PostMapping("/{id}/trigger")
    public ResponseEntity<Void> triggerReminder(@PathVariable Long id) {
        reminderService.triggerReminder(id);
        return ResponseEntity.noContent().build();
    }
}
