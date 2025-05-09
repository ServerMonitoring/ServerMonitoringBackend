package com.example.back.service;

import com.example.back.dto.request.ServerCreateRequestDTO;

public interface ServerService {
    String addServer(ServerCreateRequestDTO requestDTO, String token);
}
