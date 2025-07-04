package com.example.demo.Service;

import java.util.List;

// import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.TaskSpecification;
import com.example.demo.DTO.FIlterTaskDTO;
import com.example.demo.Model.Task;
import com.example.demo.Repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public List<Task> filterTasks(FIlterTaskDTO filter) {
        // Specification<Task> spec = Specification.wher
        // (TaskSpecification.hasTitle(filter.getTitle()))
        //         .and(TaskSpecification.hasDueDate(filter.getDueDate()))
        //         .and(TaskSpecification.hasStatus(filter.getStatus()))
        //         .and(TaskSpecification.hasPriority(filter.getPriority()));
        return taskRepository.findAll(
        TaskSpecification.hasTitle(filter.getTitle()).
        and(TaskSpecification.hasStatus(filter.getStatus())).
        and(TaskSpecification.hasDueDate(filter.getDueDate()))); // Replace with actual filtering logic
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task task) {
        if (taskRepository.existsById(taskId)) {
            task.setId(taskId);
            return taskRepository.save(task);
        }
        return null;
    }

    public boolean deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }
}
