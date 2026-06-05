package com.example.aep2b.dto;

import com.example.aep2b.model.HistoricoSolicitacaoModel;

import java.time.LocalDateTime;

public record HistoricoResponse(
        String status,
        String responsavel,
        String comentario,
        LocalDateTime data
) {
    public static HistoricoResponse from(HistoricoSolicitacaoModel h) {
        return new HistoricoResponse(
                h.getStatus().name(),
                h.getResponsavel(),
                h.getComentario(),
                h.getData()
        );
    }
}
