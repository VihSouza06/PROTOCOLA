package com.example.aep2b.controller;

import com.example.aep2b.dto.Dtos;
import com.example.aep2b.enums.UserRole;
import com.example.aep2b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String paginaLogin(@RequestParam(required = false) String erro,
                              @RequestParam(required = false) String logout,
                              Model model) {
        if (erro != null) model.addAttribute("erro", "Login ou senha incorretos.");
        if (logout != null) model.addAttribute("mensagem", "Você saiu do sistema.");
        return "auth/login";
    }

    @GetMapping("/registro")
    public String paginaRegistro() {
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String login,
                            @RequestParam String password,
                            @RequestParam String role,
                            Model model) {
        try {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            userService.registrar(new Dtos.RegistrarUsuarioRequest(login, password, userRole));
            return "redirect:/login?cadastro=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "auth/registro";
        }
    }

    // Após o login, redireciona para a área correta
    @GetMapping("/home")
    public String home(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GESTOR"))) {
            return "redirect:/gestor/painel";
        }
        return "redirect:/cidadao/nova-solicitacao";
    }
}
