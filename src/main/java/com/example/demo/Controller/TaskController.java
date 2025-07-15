package com.example.demo.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.demo.DTO.FIlterTaskDTO;
import com.example.demo.Model.Task;
import com.example.demo.Service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {   

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping
    public ResponseEntity<List<Task>> listTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 se a lista estiver vazia
        }
        return ResponseEntity.ok(tasks); // 200 OK com a lista
    }

    @GetMapping("/{taskID}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long taskID) {
        Task task = taskService.findById(taskID);
        return new ResponseEntity<>(task, HttpStatus.OK); // 200 OK
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(@ModelAttribute FIlterTaskDTO filter) {
        List<Task> tasks = taskService.filterTasks(filter);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 se a lista estiver vazia
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK); // 200 OK com a lista
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED); // 201 Created
    }

    @PutMapping("/{taskID}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskID, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskID, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK); // 200 OK
    }

    @DeleteMapping("/{taskID}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long taskID) {
        Task deletedTask = taskService.deleteTask(taskID);
        return new ResponseEntity<>(deletedTask, HttpStatus.OK); // 200 OK (ou 204 No Content se preferir para deleção)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String invalidParameter = ex.getName();
        Object receivedValue = ex.getValue();
        Class<?> requiredType = ex.getRequiredType();
        
        String errorMessage = String.format(
            "Parameter '%s' received value '%s', which is not a valid type. Expected: %s",
            invalidParameter,
            receivedValue != null ? receivedValue.toString() : "null",
            requiredType != null ? requiredType.getSimpleName() : "unknown"
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
}
