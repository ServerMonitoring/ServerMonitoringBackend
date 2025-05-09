package com.example.back.service;

import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.model.Users;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface UserService {
    Optional<Users> findUserByLogin(String login);

    void verifyUserExistenceByLogin(String login);

    @Transactional
    Users registerUser(AuthUserRequestDTO requestDTO);

    @Transactional
    void createAdmin();
}
