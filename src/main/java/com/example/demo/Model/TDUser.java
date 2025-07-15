package com.example.demo.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class TDUser {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;
    @Schema(description = "Nome de usuário", example = "jão123")
    private String username;
    @Schema(description = "Senha do usuário", example = "senhaSegura123")
    private String password;
    @Schema(description = "Email do usuário", example = "jão123@gmail.com")
    private String email;
    @Schema(description = "Papel do usuário", example = "ADMIN/USER")
    private String role;
    
    public TDUser() {
    }
}
