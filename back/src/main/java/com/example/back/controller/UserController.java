package com.example.back.controller;

import com.example.back.dto.request.UserUpdateRequestDTO;
import com.example.back.dto.response.UserResponseDTO;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }

        String userToken = token.substring("Bearer ".length());
        UserResponseDTO userResponseDTO = userService.getUser(userToken);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
    @PostMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody UserUpdateRequestDTO requestDTO){
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }

        String userToken = token.substring("Bearer ".length());
        UserResponseDTO userResponseDTO = userService.updateUser(userToken, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }

        String userToken = token.substring("Bearer ".length());
        userService.deleteUser(userToken);
        return ResponseEntity.status(HttpStatus.OK).body("User was deleted");
    }
}
