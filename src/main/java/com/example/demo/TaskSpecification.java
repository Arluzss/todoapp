package com.example.demo;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import com.example.demo.Model.Task;

public class TaskSpecification {
    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filter applied
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Task> hasDueDate(LocalDate dueDate) {
        return (root, query, criteriaBuilder) -> {
            if (dueDate == null) {
                return criteriaBuilder.conjunction(); // No filter applied
            }
            return criteriaBuilder.equal(root.get("dueDate"), dueDate);
        };
    }

    public static Specification<Task> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filter applied
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

}
