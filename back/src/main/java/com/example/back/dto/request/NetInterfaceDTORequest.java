package com.example.back.dto.request;

import com.example.back.model.NetInterface;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetInterfaceDTORequest {
    //private String interfaceName;
    private Double sent;
    private Double recv;
    @JsonProperty("packets_sent")
    private Double packetsSent;
    @JsonProperty("packets_recv")
    private Double packetsRecv;
    @JsonProperty("err_in")
    private Integer errIn;
    @JsonProperty("err_out")
    private Integer errOut;
    @JsonProperty("drop_in")
    private Integer dropIn;
    @JsonProperty("drop_out")
    private Integer dropOut;

    public static NetInterface toModel(NetInterfaceDTORequest netInterfaceDTORequest) {
        NetInterface netInterface = new NetInterface();
        //netInterface.setInterfaceName(netInterfaceDTORequest.getInterfaceName());
        netInterface.setSent(netInterfaceDTORequest.getSent());
        netInterface.setRecv(netInterfaceDTORequest.getRecv());
        netInterface.setPacketsSent(netInterfaceDTORequest.getPacketsSent());
        netInterface.setPacketsRecv(netInterfaceDTORequest.getPacketsRecv());
        netInterface.setErrIn(netInterfaceDTORequest.getErrIn());
        netInterface.setErrOut(netInterfaceDTORequest.getErrOut());
        netInterface.setDropIn(netInterfaceDTORequest.getDropIn());
        netInterface.setDropOut(netInterfaceDTORequest.getDropOut());
        return netInterface;

    }
}
