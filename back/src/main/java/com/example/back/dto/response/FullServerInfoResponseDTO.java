package com.example.back.dto.response;

import com.example.back.model.Server;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FullServerInfoResponseDTO {
    private Long id;
    private String hostname;
    private String osInfo;
    private String address;
    private String serverName;
    private String addInfo;
    private Boolean online;
    private Instant lastSeenAt;
    private String cpuModel;
    private Integer cpuCountCores;
    private Integer cpuCountCoresPhysical;
    private Double minFreq;
    private Double maxFreq;

    public static FullServerInfoResponseDTO toDTO(Server server){
        return FullServerInfoResponseDTO.builder()
                .id(server.getServerId())
                .hostname(server.getHostname())
                .osInfo(server.getOsInfo())
                .address(server.getAddress())
                .serverName(server.getServerName())
                .addInfo(server.getAddInfo())
                .online(server.getOnline())
                .lastSeenAt(server.getLastSeenAt())
                .cpuModel(server.getCpuModel())
                .cpuCountCores(server.getCpuCountCores())
                .cpuCountCoresPhysical(server.getCpuCountCoresPhysical())
                .minFreq(server.getMinFreq())
                .maxFreq(server.getMaxFreq())
                .build();
    }
}
