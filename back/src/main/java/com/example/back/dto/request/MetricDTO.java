package com.example.back.dto.request;

import java.time.Instant;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MetricDTO {
    private Instant timestamp;
    private Long uptime;
    private Double netSent;
    private Double netRecv;
    private Integer netErrors;
    private Integer netDrops;
    private Integer failedLogins;
    private Integer activeConnections;
    private Double diskTotalUsedPercent;
    private Double diskTotalAvailable;
    private MemoryDTO memory;
    private SwapDTO swap;
    private CPUDTO cpu;
    private NetworkConnectionDTO networkConnection;
    private List<DiskDTO> disks;
    private List<DiskIODTO> diskIo;
    private List<GPUDTO> gpu;
    private List<NetInterfaceDTO> netInterfaces;

}
