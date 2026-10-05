package com.example.back.service;

import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.UserUpdateRequestDTO;

import com.example.back.dto.response.UserForAdminResponseDTO;
import com.example.back.dto.response.UserResponseDTO;
import com.example.back.model.Users;
import com.example.back.model.enums.Role;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<Users> findUserByLogin(String login);

    void verifyUserExistenceByLogin(String login);

    UserResponseDTO getUser(String token);

    UserResponseDTO updateUser(String token, UserUpdateRequestDTO updateRequestDTO);

    void deleteUser(String token);

    void deleteUser(Long id);

    UserForAdminResponseDTO updateUserRole(Long id, Role role);

    List<UserForAdminResponseDTO> getAllUsersForAdmin();

    @Transactional
    Users registerUser(AuthUserRequestDTO requestDTO);

    @Transactional
    void createAdmin(String login, String password);
}
