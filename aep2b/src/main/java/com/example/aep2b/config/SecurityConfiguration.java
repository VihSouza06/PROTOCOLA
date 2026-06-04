package com.example.aep2b.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Arquivos estáticos sempre públicos
                        .requestMatchers("/", "/index.html", "/*.html", "/*.css", "/*.js").permitAll()
                        // API pública
                        .requestMatchers("/api/auth/login", "/api/auth/registro", "/api/auth/me").permitAll()
                        // H2
                        .requestMatchers("/h2-console/**").permitAll()
                        // Cidadão
                        .requestMatchers(HttpMethod.POST, "/api/solicitacoes").hasAnyRole("USER", "GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/solicitacoes/protocolo/**").hasAnyRole("USER", "GESTOR")
                        // Gestor
                        .requestMatchers(HttpMethod.GET, "/api/solicitacoes").hasRole("GESTOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/solicitacoes/**").hasRole("GESTOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
