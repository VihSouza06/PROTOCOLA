package com.example.aep2b;

import com.example.aep2b.enums.UserRole;
import com.example.aep2b.model.UserModel;
import com.example.aep2b.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DataLoader {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        if (usuarioRepository.count() == 0) {

            UserModel gestor = new UserModel(
                    "gestor",
                    passwordEncoder.encode("123456"),
                    UserRole.GESTOR
            );

            usuarioRepository.save(gestor);

            UserModel cidadao = new UserModel(
                    "cidadao",
                    passwordEncoder.encode("123456"),
                    UserRole.CIDADAO
            );

            usuarioRepository.save(cidadao);
        }
    }
}

