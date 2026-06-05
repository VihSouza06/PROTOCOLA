package com.example.aep2b.controller;

import com.example.aep2b.dto.AtualizarStatusRequest;
import com.example.aep2b.dto.CriarSolicitacaoRequest;
import com.example.aep2b.dto.ProtocoloResponse;
import com.example.aep2b.dto.SolicitacaoResponse;
import com.example.aep2b.enums.Categoria;
import com.example.aep2b.enums.Prioridade;
import com.example.aep2b.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CriarSolicitacaoRequest request) {
        try {
            String protocolo = solicitacaoService.criarSolicitacao(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ProtocoloResponse
                            (protocolo, "Solicitação registrada! Guarde o protocolo."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<?> buscarPorProtocolo(@PathVariable String protocolo) {
        try {
            return ResponseEntity.ok(solicitacaoService.buscarPorProtocolo(protocolo));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<SolicitacaoResponse>> listar(
            @RequestParam(required = false) Prioridade prioridade,
            @RequestParam(required = false) Categoria categoria,
            @RequestParam(required = false) String endereco) {

        boolean semFiltros = prioridade == null && categoria == null
                && (endereco == null || endereco.isBlank());

        List<SolicitacaoResponse> lista = semFiltros
                ? solicitacaoService.listarTodas()
                : solicitacaoService.listarComFiltros(prioridade, categoria,
                (endereco != null && endereco.isBlank()) ? null : endereco);

        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{protocolo}/status")
    public ResponseEntity<?> atualizar(@PathVariable String protocolo,
                                       @RequestBody AtualizarStatusRequest request) {
        try {
            solicitacaoService.atualizarStatus(protocolo, request);
            return ResponseEntity.ok(Map.of("mensagem", "Status atualizado com sucesso."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }
}
