package com.example.back.service;

import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.UserUpdateRequestDTO;
import com.example.back.dto.response.UserResponseDTO;
import com.example.back.model.Users;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface UserService {
    Optional<Users> findUserByLogin(String login);

    void verifyUserExistenceByLogin(String login);

    UserResponseDTO getUser(String token);

    UserResponseDTO updateUser(String token, UserUpdateRequestDTO updateRequestDTO);

    void deleteUser(String token);

    @Transactional
    Users registerUser(AuthUserRequestDTO requestDTO);

    @Transactional
    void createAdmin();
}
