package com.example.demo.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskID}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskID) {
        Task task = taskService.getTaskById(taskID);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(@ModelAttribute FIlterTaskDTO filter) {
        List<Task> tasks = taskService.filterTasks(filter);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);

    }
    
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(Task task) {
        Task updatedTask = taskService.updateTask(task.getId(), task);
        if (updatedTask != null) {
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{taskID}")
    public ResponseEntity<Long> deleteTask(@PathVariable Long taskID) {
        boolean sucess = taskService.deleteTask(taskID);
        if(sucess) {
            return new ResponseEntity<>(taskID, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(taskID, HttpStatus.NOT_FOUND);
    }

}
