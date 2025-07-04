package com.example.demo.DTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record FIlterTaskDTO(
        @Schema(description = "Título da tarefa", example = "Comprar pão") 
        String title,
        @Schema(description = "Data de vencimento da tarefa", example = "2023-10-01") 
        String dueDate,
        @Schema(description = "Status da tarefa", example = "Pendente") 
        String status) 
        {
            public String getTitle() {
                return title == null ? "" : title;
            }
            public LocalDate getDueDate() {
                return dueDate == null || dueDate.isEmpty() ? null : LocalDate.parse(dueDate);
            }
            public String getStatus() {
                return status == null ? "" : status;
            }
        }
