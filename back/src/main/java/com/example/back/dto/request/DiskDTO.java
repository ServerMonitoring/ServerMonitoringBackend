package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskDTO {
    private String mountpoint;
    private Long diskTotal;
    private Long diskUsed;
    private Long diskFree;
    private Double diskUsedPercent;

}