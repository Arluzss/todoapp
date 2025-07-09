package com.example.demo.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.Service.TaskService;
import com.example.demo.Model.Task;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

// @SpringBootTest
// @AutoConfigureMockMvc
@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void createTask() throws Exception {
        Task task = new Task(null, "Comprar pão", "Comprar pão na padaria da esquina", LocalDate.of(2023, 10, 1), "Pendente");

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
            .contentType("application/json")
            .content("{\"title\":\"Comprar pão\", \"description\":\"Comprar pão na padaria da esquina\", \"dueDate\":\"2023-10-01\", \"status\":\"Pendente\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Comprar pão"))
            .andExpect(jsonPath("$.description").value("Comprar pão na padaria da esquina"))
            .andExpect(jsonPath("$.dueDate").value("2023-10-01"))
            .andExpect(jsonPath("$.status").value("Pendente"));
    }

    @Test
    void searchByID() throws Exception {
        Long existID = 1L;
        Task task = new Task(existID, "Comprar pão", "Comprar pão na padaria da esquina", LocalDate.of(2023, 10, 1) , "Pendente");

        when(taskService.findById(existID)).thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", existID))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(existID))
            .andExpect(jsonPath("$.title").value("Comprar pão"))
            .andExpect(jsonPath("$.description").value("Comprar pão na padaria da esquina"))
            .andExpect(jsonPath("$.dueDate").value("2023-10-01"))
            .andExpect(jsonPath("$.status").value("Pendente"));
    }

    @Test
    void searchByIDNotFound() throws Exception {
        Long notExistID = 999L;

        when(taskService.findById(notExistID)).thenReturn(null);

        mockMvc.perform(get("/tasks/{id}", notExistID))
            .andExpect(status().isNotFound());
    }

    @Test
    void getAllTasks() throws Exception {
        Task task1 = new Task(1L, "Comprar pão", "Comprar pão na padaria da esquina", LocalDate.of(2023, 10, 1), "Pendente");
        Task task2 = new Task(2L, "Estudar Java", "Estudar conceitos de Spring Boot", LocalDate.of(2023, 10, 2), "Pendente");

        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Comprar pão"))
            .andExpect(jsonPath("$[1].title").value("Estudar Java"));
    }

    @Test
    void updateTask() throws Exception {
        Long existID = 1L;
        Task updatedTask = new Task(existID, "Comprar pão", "Comprar pão na padaria da esquina", LocalDate.of(2023, 10, 1), "Pendente");

        when(taskService.updateTask(eq(existID), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", existID)
            .contentType("application/json")
            .content("{\"title\":\"Comprar pão\", \"description\":\"Comprar pão na padaria da esquina\", \"dueDate\":\"2023-10-01\", \"status\":\"Pendente\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(existID))
            .andExpect(jsonPath("$.title").value("Comprar pão"))
            .andExpect(jsonPath("$.description").value("Comprar pão na padaria da esquina"))
            .andExpect(jsonPath("$.dueDate").value("2023-10-01"))
            .andExpect(jsonPath("$.status").value("Pendente"));
    }

    @Test
    void deleteTask() throws Exception {
        Long existID = 1L;

        when(taskService.deleteTask(existID)).thenReturn(true);

        mockMvc.perform(delete("/tasks/{id}", existID))
            .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(existID);
    }

}
