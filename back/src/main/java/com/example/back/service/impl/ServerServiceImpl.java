package com.example.back.service.impl;

import com.example.back.dto.request.ServerCreateRequestDTO;
import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Server;
import com.example.back.model.Users;
import com.example.back.repository.ServerRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.ServerService;
import com.example.back.service.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ServerServiceImpl(ServerRepository serverRepository, JwtService jwtService, UserRepository userRepository) {
        this.serverRepository = serverRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public String addServer(ServerCreateRequestDTO requestDTO, String token) {
        Server server = new Server();
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));

        server.setUsers(user);


        Optional.ofNullable(requestDTO.getAddress()).ifPresent(server::setAddress);
        Optional.ofNullable(requestDTO.getAddInfo()).ifPresent(server::setAddInfo);

        Server savedServer = serverRepository.save(server);

        return jwtService.generateNodeToken(savedServer.getServerId(),id);
    }

    @Override
    public String updateNodeToken(Long serverId, String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        Server server = serverRepository.findById(serverId).orElseThrow(()-> new UserNotFoundException("Server not found"));

        if (!user.getUserId().equals(server.getUsers().getUserId())) {
            throw new UserNotFoundException("Server does not belong to user");
        }

        return jwtService.generateNodeToken(server.getServerId(),id);
    }
}
