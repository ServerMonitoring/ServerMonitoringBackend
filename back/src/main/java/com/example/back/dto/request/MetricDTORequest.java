package com.example.back.dto.request;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("failed_logins")
    private Integer failedLogins;
    private Integer activeConnections;
    private Double diskTotalUsedPercent;
    private Double diskTotalAvailable;
    private MemoryDTORequest memory;
    private SwapDTORequest swap;
    private CPUDTORequest cpu;
    @JsonProperty("network_connections")
    private NetworkConnectionDTORequest networkConnection;
    @JsonProperty("disk_partitions")
    private List<DiskDTORequest> disks;
    @JsonProperty("disk_io")
    private Map<String, DiskIODTORequest> diskIo;
    private List<GPUDTORequest> gpu;
    @JsonProperty("net_interfaces")
    private List<NetInterfaceDTORequest> netInterfaces;

}
