package com.example.back.service;

import com.example.back.dto.request.ServerCreateRequestDTO;
import com.example.back.dto.request.ServerUpdateRequestDTO;
import com.example.back.dto.response.FullServerInfoResponseDTO;
import com.example.back.dto.response.MinServerInfoResponseDTO;
import com.example.back.dto.response.StaticMetricServerResponseDTO;
import com.example.back.dto.search.ServerSearchCriteria;
import com.example.back.model.Server;

import java.util.List;

public interface ServerService {
    String addServer(ServerCreateRequestDTO requestDTO, String token);

    String updateNodeToken(Long serverId, String token);

    List<MinServerInfoResponseDTO> getMinServerInfo (ServerSearchCriteria criteria, String token);

    List<FullServerInfoResponseDTO> getFullServerInfo(ServerSearchCriteria criteria, String token);

    List<StaticMetricServerResponseDTO> getStaticMetricServer(ServerSearchCriteria criteria, String token);

    MinServerInfoResponseDTO updateServer(ServerUpdateRequestDTO requestDTO, String token);

    void deleteServer(Long serverId, String token);
}
