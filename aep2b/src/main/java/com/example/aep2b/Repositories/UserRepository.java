package com.example.aep2b.Repositories;

import com.example.aep2b.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserDetails findByLogin(String role);
}
