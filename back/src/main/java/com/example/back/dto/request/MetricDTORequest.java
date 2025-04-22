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
public class MetricDTORequest {
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
    private MemoryDTORequest memory;
    private SwapDTORequest swap;
    private CPUDTORequest cpu;
    private NetworkConnectionDTORequest networkConnection;
    private List<DiskDTORequest> disks;
    private List<DiskIODTORequest> diskIo;
    private List<GPUDTORequest> gpu;
    private List<NetInterfaceDTORequest> netInterfaces;

}
