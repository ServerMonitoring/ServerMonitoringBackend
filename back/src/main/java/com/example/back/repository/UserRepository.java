package com.example.back.repository;

import com.example.back.model.Users;
import com.example.back.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByLogin(String login);

    boolean existsByRole(Role role);
}
