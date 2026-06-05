package com.example.aep2b.dto;

import com.example.aep2b.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarSolicitacaoRequest(
        @NotNull(message = "Categoria é obrigatória") Categoria categoria,
        @NotBlank(message = "Descrição é obrigatória") String descricao,
        @NotBlank(message = "Endereço é obrigatório") String endereco,
        boolean anonimo,
        String nomeSolicitante
) {}
