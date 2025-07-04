package com.example.demo.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

//título, descrição, data de vencimento, status

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;
    @Schema(description = "Título da tarefa", example = "Comprar pão")
    private String title;
    @Schema(description = "Descrição da tarefa", example = "Comprar pão na padaria da esquina")
    private String description;
    @Schema(description = "Data de vencimento da tarefa", example = "2023-10-01")
    private String dueDate;
    @Schema(description = "Status da tarefa", example = "Pendente")
    private String status;
}
