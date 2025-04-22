package com.example.back.dto.request;

import com.example.back.model.NetInterface;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetInterfaceDTORequest {
    private String interfaceName;
    private Double sent;
    private Double recv;
    private Integer err_in;
    private Integer err_out;
    private Integer drop_in;
    private Integer drop_out;

    public static NetInterface toModel(NetInterfaceDTORequest netInterfaceDTORequest) {
        NetInterface netInterface = new NetInterface();
        netInterface.setInterfaceName(netInterfaceDTORequest.getInterfaceName());
        netInterface.setSent(netInterfaceDTORequest.getSent());
        netInterface.setRecv(netInterfaceDTORequest.getRecv());
        netInterface.setErr_in(netInterfaceDTORequest.getErr_in());
        netInterface.setErr_out(netInterfaceDTORequest.getErr_out());
        netInterface.setDrop_in(netInterfaceDTORequest.getDrop_in());
        netInterface.setDrop_out(netInterfaceDTORequest.getDrop_out());
        return netInterface;

    }
}
