package com.example.back.controller;

import com.example.back.dto.request.ServerCreateRequestDTO;
import com.example.back.dto.request.ServerUpdateRequestDTO;
import com.example.back.dto.response.FullServerInfoResponseDTO;
import com.example.back.dto.response.MinServerInfoResponseDTO;
import com.example.back.dto.search.ServerSearchCriteria;
import com.example.back.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/min_info")
    public ResponseEntity<List<MinServerInfoResponseDTO>> getMinServerInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody(required = false)ServerSearchCriteria criteria) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        String userToken = token.substring("Bearer ".length());

        if (criteria == null) {
            criteria = new ServerSearchCriteria();
        }

        List<MinServerInfoResponseDTO> servers = serverService.getMinServerInfo(criteria, userToken);
        return ResponseEntity.status(HttpStatus.OK).body(servers);
    }

    @PostMapping("/full_info")
    public ResponseEntity<List<FullServerInfoResponseDTO>> getFullServerInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody ServerSearchCriteria criteria) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        String userToken = token.substring("Bearer ".length());

        if (criteria == null) {
            criteria = new ServerSearchCriteria();
        }

        List<FullServerInfoResponseDTO> servers = serverService.getFullServerInfo(criteria, userToken);
        return ResponseEntity.status(HttpStatus.OK).body(servers);
    }

    @PostMapping("/update")
    public ResponseEntity<MinServerInfoResponseDTO> updateServer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody ServerUpdateRequestDTO server) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        String userToken = token.substring("Bearer ".length());

        MinServerInfoResponseDTO serverInfo = serverService.updateServer(server, userToken);
        return ResponseEntity.status(HttpStatus.OK).body(serverInfo);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteServer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody Long serverId) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        String userToken = token.substring("Bearer ".length());
        serverService.deleteServer(serverId, userToken);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted server with id " + serverId);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshNodeToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Long serverId ) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }
        String userToken = token.substring("Bearer ".length());
        String nodeToken = serverService.updateNodeToken(serverId, userToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(nodeToken);
    }
}
