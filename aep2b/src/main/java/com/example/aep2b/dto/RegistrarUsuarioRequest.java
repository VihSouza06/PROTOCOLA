package com.example.aep2b.dto;

import com.example.aep2b.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarUsuarioRequest(
        @NotBlank(message = "Login é obrigatório") String login,
        @NotBlank(message = "Senha é obrigatória") String password,
        @NotNull(message = "Perfil é obrigatório") UserRole role
) {}
