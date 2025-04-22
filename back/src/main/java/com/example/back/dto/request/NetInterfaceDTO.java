package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetInterfaceDTO {
    private String interfaceName;
    private Double sent;
    private Double recv;
    private Integer err_in;
    private Integer err_out;
    private Integer drop_in;
    private Integer drop_out;
}
