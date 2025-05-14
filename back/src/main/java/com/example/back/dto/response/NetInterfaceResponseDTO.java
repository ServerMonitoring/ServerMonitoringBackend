package com.example.back.dto.response;

import com.example.back.model.NetInterface;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetInterfaceResponseDTO {
    private Long id;
    private String interfaceName;
    private Double sent;
    private Double recv;
    private Double packetsSent;
    private Double packetsRecv;
    private Integer errIn;
    private Integer errOut;
    private Integer dropIn;
    private Integer dropOut;

    public static NetInterfaceResponseDTO toDTO(NetInterface netInterface) {
        return NetInterfaceResponseDTO.builder()
                .id(netInterface.getNetInterfaceId())
                .interfaceName(netInterface.getInterfaceName())
                .sent(netInterface.getSent())
                .recv(netInterface.getRecv())
                .packetsSent(netInterface.getPacketsSent())
                .packetsRecv(netInterface.getPacketsRecv())
                .errIn(netInterface.getErrIn())
                .errOut(netInterface.getErrOut())
                .dropIn(netInterface.getDropIn())
                .dropOut(netInterface.getDropOut())
                .build();
    }
}
