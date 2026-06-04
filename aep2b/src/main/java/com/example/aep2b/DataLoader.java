package com.example.aep2b;

import com.example.aep2b.dto.Dtos;
import com.example.aep2b.enums.UserRole;
import com.example.aep2b.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        criar("cidadao", "123456", UserRole.CIDADAO);
        criar("gestor",  "123456", UserRole.GESTOR);
        System.out.println("=== Usuários de teste criados: cidadao/123456 e gestor/123456 ===");
    }

    private void criar(String login, String senha, UserRole role) {
        try {
            userService.registrar(new Dtos.RegistrarUsuarioRequest(login, senha, role));
        } catch (IllegalArgumentException ignored) {}
    }
}

