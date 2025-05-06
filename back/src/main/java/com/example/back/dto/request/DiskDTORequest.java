package com.example.back.dto.request;

import com.example.back.model.Disk;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiskDTORequest {
    private String mountpoint;
    @JsonProperty("total")
    private Long diskTotal;
    @JsonProperty("used")
    private Long diskUsed;
    @JsonProperty("free")
    private Long diskFree;
    @JsonProperty("used_percent")
    private Double diskUsedPercent;

    public static Disk toModel(DiskDTORequest diskDTORequest) {
        Disk disk = new Disk();
        disk.setMountpoint(diskDTORequest.getMountpoint());
        disk.setDiskTotal(diskDTORequest.getDiskTotal());
        disk.setDiskUsed(diskDTORequest.getDiskUsed());
        disk.setDiskFree(diskDTORequest.getDiskFree());
        disk.setDiskUsedPercent(diskDTORequest.getDiskUsedPercent());
        return disk;

    }
}