package com.example.back.dto.response;

import com.example.back.model.GPU;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GPUResponseDTO {
    private Long id;
    private String gpuName;
    private Double loadPercent;
    private Double memoryTotal;
    private Double memoryUsed;
    private Double memoryFree;
    private Double memoryUsedPercent;
    private Double temperature;

    public static GPUResponseDTO toDTO(GPU gpu) {
        return GPUResponseDTO.builder()
                .id(gpu.getGpuId())
                .gpuName(gpu.getGpuName())
                .loadPercent(gpu.getLoadPercent())
                .memoryTotal(gpu.getMemoryTotal())
                .memoryUsed(gpu.getMemoryUsed())
                .memoryFree(gpu.getMemoryFree())
                .memoryUsedPercent(gpu.getMemoryUsedPercent())
                .temperature(gpu.getTemperature())
                .build();
    }
}
