package com.example.back.service.impl;

import com.example.back.dto.request.ServerCreateRequestDTO;
import com.example.back.dto.request.ServerUpdateRequestDTO;
import com.example.back.dto.response.FullServerInfoResponseDTO;
import com.example.back.dto.response.MinServerInfoResponseDTO;
import com.example.back.dto.response.StaticMetricServerResponseDTO;
import com.example.back.dto.search.ServerSearchCriteria;
import com.example.back.exception.RequestArgumentException;
import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Server;
import com.example.back.model.Users;
import com.example.back.repository.ServerRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.ServerService;
import com.example.back.service.security.JwtService;
import com.example.back.util.criteriaSpecification.ServerSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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

        Optional.ofNullable(requestDTO.getServerName()).ifPresent(server::setServerName);
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

    @Override
    public List<MinServerInfoResponseDTO> getMinServerInfo (ServerSearchCriteria criteria, String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        criteria.setUserId(user.getUserId());
        Specification<Server> serverSpecification = ServerSpecification.byCriteria(criteria);

        List<Server> servers = serverRepository.findAll(serverSpecification);
        return servers.stream().map(MinServerInfoResponseDTO::toDTO).toList();
    }

    @Override
    public List<FullServerInfoResponseDTO> getFullServerInfo (ServerSearchCriteria criteria, String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        criteria.setUserId(user.getUserId());
        Specification<Server> serverSpecification = ServerSpecification.byCriteria(criteria);

        List<Server> servers = serverRepository.findAll(serverSpecification);
        return servers.stream().map(FullServerInfoResponseDTO::toDTO).toList();
    }

    @Override
    public List<StaticMetricServerResponseDTO> getStaticMetricServer (ServerSearchCriteria criteria, String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        criteria.setUserId(user.getUserId());
        Specification<Server> serverSpecification = ServerSpecification.byCriteria(criteria);

        List<Server> servers = serverRepository.findAll(serverSpecification);
        return servers.stream().map(StaticMetricServerResponseDTO::toDTO).toList();
    }

    @Override
    public MinServerInfoResponseDTO updateServer(ServerUpdateRequestDTO requestDTO, String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        Server server = serverRepository.findById(requestDTO.getId()).orElseThrow(()-> new UserNotFoundException("Server not found"));

        if (!user.getUserId().equals(server.getUsers().getUserId())) {
            throw new RequestArgumentException("User does not belong to user");
        }

        Optional.ofNullable(requestDTO.getServerName()).ifPresent(server::setServerName);
        Optional.ofNullable(requestDTO.getAddress()).ifPresent(server::setAddress);
        Optional.ofNullable(requestDTO.getAddInfo()).ifPresent(server::setAddInfo);
        Server savedServer = serverRepository.save(server);
        return MinServerInfoResponseDTO.toDTO(savedServer);
    }

    @Override
    public void deleteServer(Long serverId, String token){
        Long id = jwtService.extractId(token);
        Users user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        Server server = serverRepository.findById(serverId).orElseThrow(()-> new UserNotFoundException("Server not found"));

        if (!user.getUserId().equals(server.getUsers().getUserId())) {
            throw new RequestArgumentException("User does not belong to user");
        }

        serverRepository.delete(server);
    }
}
