package com.example.back.controller;


import com.example.back.dto.request.AuthUserRequestDTO;
import com.example.back.dto.request.SignInRequestDTO;
import com.example.back.model.Users;
import com.example.back.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<String> signUpUser(@RequestBody AuthUserRequestDTO requestDTO) {
        Optional<Users> user = Optional.ofNullable(authenticationService.signUp(requestDTO));
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sign up failed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Sign up successful");
    }

    @PostMapping("/sign_in")
    public ResponseEntity<String> signInUser(@RequestBody SignInRequestDTO requestDTO) {
        String token = authenticationService.signIn(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
