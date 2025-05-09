package com.example.back.service.security;

import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.SignInRequestDTO;
import com.example.back.exception.AuthenticationFailedException;
import com.example.back.model.Users;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;


    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, @Qualifier("customUserDetailsService") CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public Users signUp(AuthUserRequestDTO requestDTO) {
        return userService.registerUser(requestDTO);
    }
    @Override
    public String signIn(SignInRequestDTO requestDTO) {
        CustomUserDetails customUserDetails;
        try{
            String effectivePassword = Optional.ofNullable(requestDTO.getPassword()).orElse("");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), effectivePassword)
            );
            customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        }catch (AuthenticationException exception){
            throw new AuthenticationFailedException("Invalid email or password");
        }

        return jwtService.generateToken(customUserDetails);
    }

}
