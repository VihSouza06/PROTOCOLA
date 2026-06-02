package com.example.aep2b.model;

import com.example.aep2b.enums.Categoria;
import com.example.aep2b.enums.Prioridade;
import com.example.aep2b.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitacoes")
@Getter
@NoArgsConstructor
public class SolicitacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private Prioridade prioridade;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String nomeSolicitante;
    private boolean anonimo;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    @OneToMany(mappedBy = "solicitacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoSolicitacaoModel> historico = new ArrayList<>();

    public SolicitacaoModel(String protocolo, Categoria categoria, String descricao,
                            String endereco, String nomeSolicitante, boolean anonimo) {
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.endereco = endereco;
        this.nomeSolicitante = anonimo ? "ANONIMO" : nomeSolicitante;
        this.anonimo = anonimo;
        this.status = Status.ABERTO;
        this.prioridade = Prioridade.MEDIA;
        this.dataSolicitacao = LocalDateTime.now();
    }
}
