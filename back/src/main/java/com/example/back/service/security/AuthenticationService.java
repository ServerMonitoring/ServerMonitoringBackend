package com.example.back.service.security;

import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.SignInRequestDTO;
import com.example.back.model.Users;

public interface AuthenticationService {
    Users signUp(AuthUserRequestDTO requestDTO);

    String signIn(SignInRequestDTO requestDTO);
}
