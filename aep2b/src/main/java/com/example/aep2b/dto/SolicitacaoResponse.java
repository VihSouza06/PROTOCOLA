package com.example.aep2b.dto;

import com.example.aep2b.model.SolicitacaoModel;

import java.time.LocalDateTime;
import java.util.List;

public record SolicitacaoResponse(
        Long id,
        String protocolo,
        String categoria,
        String descricao,
        String endereco,
        String status,
        String prioridade,
        String nomeSolicitante,
        boolean anonimo,
        LocalDateTime dataCriacao,
        List<HistoricoResponse> historico
) {
    public static SolicitacaoResponse from(SolicitacaoModel s) {
        return new SolicitacaoResponse(
                s.getId(),
                s.getProtocolo(),
                s.getCategoria().getDescricao(),
                s.getDescricao(),
                s.getEndereco(),
                s.getStatus().name(),
                s.getPrioridade().name(),
                s.getNomeSolicitante(),
                s.isAnonimo(),
                s.getDataSolicitacao(),
                s.getHistorico().stream().map(HistoricoResponse::from).toList()
        );
    }
}