package com.example.aep2b.Controllers;

import com.example.aep2b.Dto.Dtos;
import com.example.aep2b.Enums.Categoria;
import com.example.aep2b.Enums.Prioridade;
import com.example.aep2b.Enums.Status;
import com.example.aep2b.Services.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    // GET /gestor/painel — lista todas as solicitações com filtros opcionais
    @GetMapping("/painel")
    public String painel(@RequestParam(required = false) Prioridade prioridade,
                         @RequestParam(required = false) Categoria categoria,
                         @RequestParam(required = false) String endereco,
                         Model model) {

        boolean semFiltros = prioridade == null && categoria == null
                && (endereco == null || endereco.isBlank());

        var lista = semFiltros
                ? solicitacaoService.listarTodas()
                : solicitacaoService.listarComFiltros(
                prioridade, categoria,
                (endereco != null && endereco.isBlank()) ? null : endereco);

        model.addAttribute("solicitacoes", lista);
        model.addAttribute("prioridades", Prioridade.values());
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("filtroPrioridade", prioridade);
        model.addAttribute("filtroCategoria", categoria);
        model.addAttribute("filtroEndereco", endereco);
        return "gestor/painel";
    }

    // GET /gestor/atualizar/{protocolo} — exibe formulário de atualização
    @GetMapping("/atualizar/{protocolo}")
    public String formularioAtualizar(@PathVariable String protocolo, Model model) {
        try {
            var solicitacao = solicitacaoService.buscarPorProtocolo(protocolo);
            model.addAttribute("solicitacao", solicitacao);
            model.addAttribute("statuses", Status.values());
            model.addAttribute("prioridades", Prioridade.values());
        } catch (NoSuchElementException e) {
            model.addAttribute("erro", "Protocolo não encontrado.");
            return "redirect:/gestor/painel";
        }
        return "gestor/atualizar";
    }

    // POST /gestor/atualizar/{protocolo} — processa atualização
    @PostMapping("/atualizar/{protocolo}")
    public String atualizar(@PathVariable String protocolo,
                            @RequestParam Status novoStatus,
                            @RequestParam Prioridade novaPrioridade,
                            @RequestParam String comentario,
                            @RequestParam String responsavel,
                            RedirectAttributes redirect) {
        try {
            var request = new Dtos.AtualizarStatusRequest(
                    novoStatus, novaPrioridade, comentario, responsavel);
            solicitacaoService.atualizarStatus(protocolo, request);
            redirect.addFlashAttribute("sucesso", "Demanda atualizada com sucesso!");
        } catch (Exception e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/gestor/painel";
    }
}
