package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.TaskSpecification;
import com.example.demo.DTO.FIlterTaskDTO;
import com.example.demo.Exception.InvalidInputException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Task;
import com.example.demo.Repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() throws RuntimeException {
        return taskRepository.findAll();
    }

    public Task findById(Long taskId) {
        return taskRepository.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    public List<Task> filterTasks(FIlterTaskDTO filter) {
        List<Task> tasks = taskRepository.findAll(
                TaskSpecification.hasTitle(filter.getTitle())
                        .and(TaskSpecification.hasStatus(filter.getStatus()))
                        .and(TaskSpecification.hasDueDate(filter.getDueDate())));
        return tasks;
    }

    public Task createTask(Task task){
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new InvalidInputException("The task title cannot be empty.");
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task task){
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " not found for update."));

        if (taskId == null || task == null) {
            throw new IllegalArgumentException("Task ID and Task object must not be null");
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());

        return taskRepository.save(existingTask);
    }

    public Task deleteTask(Long taskId){
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " not found for deletion."));
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }
}
