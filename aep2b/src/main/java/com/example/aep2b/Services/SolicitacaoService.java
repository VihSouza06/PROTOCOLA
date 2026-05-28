package com.example.aep2b.Services;

import com.example.aep2b.Dto.Dtos;
import com.example.aep2b.Enums.Categoria;
import com.example.aep2b.Enums.Prioridade;
import com.example.aep2b.Models.HistoricoSolicitacaoModel;
import com.example.aep2b.Models.SolicitacaoModel;
import com.example.aep2b.Repositories.HistoricoRepository;
import com.example.aep2b.Repositories.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private HistoricoRepository historicoRepository;

    // ---- Operações do Cidadão ----

    @Transactional
    public String criarSolicitacao(Dtos.CriarSolicitacaoRequest request) {
        validarDadosSolicitante(request);

        String protocolo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        SolicitacaoModel solicitacao = new SolicitacaoModel(
                protocolo,
                request.categoria(),
                request.descricao(),
                request.endereco(),
                request.nomeSolicitante(),
                request.anonimo()
        );
        solicitacaoRepository.save(solicitacao);

        registrarHistoricoInicial(solicitacao);
        return protocolo;
    }

    public Dtos.SolicitacaoResponse buscarPorProtocolo(String protocolo) {
        SolicitacaoModel solicitacao = solicitacaoRepository
                .findByProtocolo(protocolo.toUpperCase())
                .orElseThrow(() -> new NoSuchElementException("Protocolo não encontrado: " + protocolo));
        return Dtos.SolicitacaoResponse.from(solicitacao);
    }

    // ---- Operações do Gestor ----

    public List<Dtos.SolicitacaoResponse> listarTodas() {
        return solicitacaoRepository.findAll()
                .stream()
                .map(Dtos.SolicitacaoResponse::from)
                .toList();
    }

    public List<Dtos.SolicitacaoResponse> listarComFiltros(
            Prioridade prioridade, Categoria categoria, String endereco) {
        return solicitacaoRepository.findComFiltros(prioridade, categoria, endereco)
                .stream()
                .map(Dtos.SolicitacaoResponse::from)
                .toList();
    }

    @Transactional
    public void atualizarStatus(String protocolo, Dtos.AtualizarStatusRequest request) {
        SolicitacaoModel solicitacao = solicitacaoRepository
                .findByProtocolo(protocolo.toUpperCase())
                .orElseThrow(() -> new NoSuchElementException("Protocolo não encontrado: " + protocolo));

        solicitacao.setStatus(request.novoStatus());
        solicitacao.setPrioridade(request.novaPrioridade());
        solicitacaoRepository.save(solicitacao);

        HistoricoSolicitacaoModel historico = new HistoricoSolicitacaoModel(
                solicitacao,
                request.novoStatus(),
                request.responsavel(),
                request.comentario()
        );
        historicoRepository.save(historico);
    }

    private void validarDadosSolicitante(Dtos.CriarSolicitacaoRequest request) {
        if (!request.anonimo() &&
                (request.nomeSolicitante() == null || request.nomeSolicitante().isBlank())) {
            throw new IllegalArgumentException(
                    "Nome do solicitante é obrigatório quando não anônimo.");
        }
    }

    private void registrarHistoricoInicial(SolicitacaoModel solicitacao) {
        HistoricoSolicitacaoModel historico = new HistoricoSolicitacaoModel(
                solicitacao,
                solicitacao.getStatus(),
                "Sistema",
                "Solicitação registrada com sucesso."
        );
        historicoRepository.save(historico);
    }
}
