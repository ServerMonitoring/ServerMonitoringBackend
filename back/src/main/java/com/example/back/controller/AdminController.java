package com.example.back.controller;


import com.example.back.dto.response.UserForAdminResponseDTO;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    //TODO добавить фильтры для поиска пользователя
    @GetMapping
    public ResponseEntity<List<UserForAdminResponseDTO>> getAllUsers() {

        List<UserForAdminResponseDTO> response = userService.getAllUsersForAdmin();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam(name = "userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
    //TODO сделать админу возможность обновить все данные пользователя в том числе и роль
    //TODO сделать взаимодействие со всеми серверами всех пользователей?
}
