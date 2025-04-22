package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NetworkConnectionDTO {
    private Integer tcp;
    private Integer udp;

}
