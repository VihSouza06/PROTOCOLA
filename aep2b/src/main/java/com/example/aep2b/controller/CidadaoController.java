package com.example.aep2b.controller;

import com.example.aep2b.dto.Dtos;
import com.example.aep2b.enums.Categoria;
import com.example.aep2b.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/cidadao")
public class CidadaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    // GET /cidadao/nova-solicitacao — exibe o formulário
    @GetMapping("/nova-solicitacao")
    public String formulario(Model model) {
        model.addAttribute("categorias", Categoria.values());
        return "cidadao/nova-solicitacao";
    }

    // POST /cidadao/nova-solicitacao — processa o formulário
    @PostMapping("/nova-solicitacao")
    public String registrar(@RequestParam Categoria categoria,
                            @RequestParam String descricao,
                            @RequestParam String endereco,
                            @RequestParam(defaultValue = "false") boolean anonimo,
                            @RequestParam(required = false) String nomeSolicitante,
                            RedirectAttributes redirect) {
        try {
            var request = new Dtos.CriarSolicitacaoRequest(
                    categoria, descricao, endereco, anonimo, nomeSolicitante);
            String protocolo = solicitacaoService.criarSolicitacao(request);
            redirect.addFlashAttribute("protocolo", protocolo);
            return "redirect:/cidadao/confirmacao";
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/cidadao/nova-solicitacao";
        }
    }

    // GET /cidadao/confirmacao — exibe o protocolo gerado
    @GetMapping("/confirmacao")
    public String confirmacao() {
        return "cidadao/confirmacao";
    }

    // GET /cidadao/acompanhar — exibe formulário de consulta
    @GetMapping("/acompanhar")
    public String formularioConsulta() {
        return "cidadao/acompanhar";
    }

    // POST /cidadao/acompanhar — busca pelo protocolo
    @PostMapping("/acompanhar")
    public String consultar(@RequestParam String protocolo, Model model) {
        try {
            var solicitacao = solicitacaoService.buscarPorProtocolo(protocolo.trim().toUpperCase());
            model.addAttribute("solicitacao", solicitacao);
        } catch (NoSuchElementException e) {
            model.addAttribute("erro", "Protocolo não encontrado: " + protocolo);
        }
        return "cidadao/acompanhar";
    }
}
