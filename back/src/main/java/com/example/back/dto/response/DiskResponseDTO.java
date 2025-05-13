package com.example.back.dto.response;

import com.example.back.model.Disk;
import lombok.*;

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

    public static DiskResponseDTO toDTO(Disk disk) {
        return DiskResponseDTO.builder()
                .id(disk.getDiskId())
                .device(disk.getDevice())
                .mountpoint(disk.getMountpoint())
                .diskTotal(disk.getDiskTotal())
                .diskUsed(disk.getDiskUsed())
                .diskFree(disk.getDiskFree())
                .diskUsedPercent(disk.getDiskUsedPercent())
                .build();
    }
}
