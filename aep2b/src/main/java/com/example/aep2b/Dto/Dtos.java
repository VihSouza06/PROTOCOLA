package com.example.aep2b.Dto;

import com.example.aep2b.Enums.Categoria;
import com.example.aep2b.Enums.Prioridade;
import com.example.aep2b.Enums.Status;
import com.example.aep2b.Enums.UserRole;
import com.example.aep2b.Models.HistoricoSolicitacaoModel;
import com.example.aep2b.Models.SolicitacaoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class Dtos {

    public record CriarSolicitacaoRequest(
            @NotNull(message = "Categoria é obrigatória") Categoria categoria,
            @NotBlank(message = "Descrição é obrigatória") String descricao,
            @NotBlank(message = "Endereço é obrigatório") String endereco,
            boolean anonimo,
            String nomeSolicitante
    ) {}

    public record AtualizarStatusRequest(
            @NotNull(message = "Status é obrigatório") Status novoStatus,
            @NotNull(message = "Prioridade é obrigatória") Prioridade novaPrioridade,
            @NotBlank(message = "Comentário é obrigatório") String comentario,
            @NotBlank(message = "Nome do responsável é obrigatório") String responsavel
    ) {}

    public record RegistrarUsuarioRequest(
            @NotBlank(message = "Login é obrigatório") String login,
            @NotBlank(message = "Senha é obrigatória") String password,
            @NotNull(message = "Perfil é obrigatório") UserRole role
    ) {}

    // Response de solicitação (o que a API devolve)
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

    public record ProtocoloResponse(String protocolo, String mensagem) {}
}
