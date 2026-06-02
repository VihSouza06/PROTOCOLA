package com.example.aep2b.model;

import com.example.aep2b.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_solicitacoes")
@Getter
@NoArgsConstructor
public class HistoricoSolicitacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_solicitacao", nullable = false)
    private SolicitacaoModel solicitacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private String responsavel;

    @Column(nullable = false, length = 1000)
    private String comentario;

    public HistoricoSolicitacaoModel(SolicitacaoModel solicitacao, Status status,
                                     String responsavel, String comentario) {
        this.solicitacao = solicitacao;
        this.status = status;
        this.responsavel = responsavel;
        this.comentario = comentario;
        this.data = LocalDateTime.now();
    }
}
