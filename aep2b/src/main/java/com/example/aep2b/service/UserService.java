package com.example.aep2b.service;

import com.example.aep2b.dto.Dtos;
import com.example.aep2b.model.UserModel;
import com.example.aep2b.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public void registrar(Dtos.RegistrarUsuarioRequest request){
        if (userRepository.existsByLogin(request.login())){
            throw new IllegalArgumentException("Login já em uso: " + request.login());
        }
        String senhaEncriptada = passwordEncoder.encode(request.password());
        UserModel user = new UserModel(request.login(), senhaEncriptada, request.role());
        userRepository.save(user);
    }
}
