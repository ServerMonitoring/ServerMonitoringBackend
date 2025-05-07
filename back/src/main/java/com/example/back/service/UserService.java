package com.example.back.service;

import com.example.back.model.Users;

import java.util.Optional;

public interface UserService {
    Optional<Users> findUserByLogin(String login);
}
