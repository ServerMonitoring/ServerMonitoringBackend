package com.example.back.controller;

import com.example.back.dto.request.ServerCreateRequestDTO;
import com.example.back.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server")
public class ServerController {

    private final ServerService serverService;

    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createServer(@RequestBody ServerCreateRequestDTO server, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }

        String userToken = token.substring("Bearer ".length());
        String nodeToken = serverService.addServer(server, userToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(nodeToken);
    }
}
