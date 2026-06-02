package com.example.aep2b.repository;

import com.example.aep2b.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserDetails> findByLogin(String login);
    boolean existsByLogin(String login);
}
