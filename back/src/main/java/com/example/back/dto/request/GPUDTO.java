package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GPUDTO {
    private String gpuName;
    private Double loadPercent;
    private Double memoryTotal;
    private Double memoryUsed;
    private Double memoryFree;
    private Double temperature;

}
