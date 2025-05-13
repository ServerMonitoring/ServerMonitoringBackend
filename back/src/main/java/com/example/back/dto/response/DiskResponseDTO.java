package com.example.back.dto.response;

import com.example.back.model.Disk;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskResponseDTO {
    private Long id;
    private String device;
    private String mountpoint;
    private Long diskTotal;
    private Long diskUsed;
    private Long diskFree;
    private Double diskUsedPercent;
    private Instant timestamp;
    private Long serverId;

    public static DiskResponseDTO toDTO(Disk disk) {
        return DiskResponseDTO.builder()
                .id(disk.getDiskId())
                .device(disk.getDevice())
                .mountpoint(disk.getMountpoint())
                .diskTotal(disk.getDiskTotal())
                .diskUsed(disk.getDiskUsed())
                .diskFree(disk.getDiskFree())
                .diskUsedPercent(disk.getDiskUsedPercent())
                .timestamp(disk.getMetric().getTimestamp())
                .serverId(disk.getMetric().getServer().getServerId())
                .build();
    }
}
