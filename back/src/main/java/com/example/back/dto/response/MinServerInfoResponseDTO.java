package com.example.back.dto.response;

import com.example.back.model.Server;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MinServerInfoResponseDTO {
    private String hostname;
    private String address;
    private String serverName;
    private String addInfo;
    private Long idserver;

    public static MinServerInfoResponseDTO toDTO(Server server){
        return MinServerInfoResponseDTO.builder()
                .idserver(server.getServerId())
                .hostname(server.getHostname())
                .address(server.getAddress())
                .serverName(server.getServerName())
                .addInfo(server.getAddInfo())
                .build();
    }
}
