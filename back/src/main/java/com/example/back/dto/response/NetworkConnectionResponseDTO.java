package com.example.back.dto.response;

import com.example.back.model.NetworkConnection;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetworkConnectionResponseDTO {
    private Long id;
    private Integer tcp;
    private Integer udp;

    public static NetworkConnectionResponseDTO toDTO(NetworkConnection networkConnection) {
        return NetworkConnectionResponseDTO.builder()
                .id(networkConnection.getNetworkConnectionId())
                .tcp(networkConnection.getTcp())
                .udp(networkConnection.getUdp())
                .build();
    }
}
