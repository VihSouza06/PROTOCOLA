package com.example.aep2b.dto;

import com.example.aep2b.enums.Prioridade;
import com.example.aep2b.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusRequest(
        @NotNull(message = "Status é obrigatório") Status novoStatus,
        @NotNull(message = "Prioridade é obrigatória") Prioridade novaPrioridade,
        @NotBlank(message = "Comentário é obrigatório") String comentario,
        @NotBlank(message = "Nome do responsável é obrigatório") String responsavel
) {}
