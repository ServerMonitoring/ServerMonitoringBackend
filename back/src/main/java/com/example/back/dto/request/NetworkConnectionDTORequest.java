package com.example.back.dto.request;

import com.example.back.model.NetworkConnection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetworkConnectionDTORequest {
    private Integer tcp;
    private Integer udp;

    public static NetworkConnection toModel(NetworkConnectionDTORequest networkConnectionDTORequest) {
        NetworkConnection networkConnection = new NetworkConnection();
        networkConnection.setTcp(networkConnectionDTORequest.getTcp());
        networkConnection.setUdp(networkConnectionDTORequest.getUdp());
        return networkConnection;
    }
}
