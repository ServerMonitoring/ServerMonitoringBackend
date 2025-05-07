package com.example.back.service.impl;

import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Users;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Users> findUserByLogin(String login) {
        Users user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User with login: "+login+" not found"));
        return Optional.of(user);
    }
}
