package com.example.back.dto.request;

import com.example.back.model.GPU;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GPUDTORequest {
    private String gpuName;
    private Double loadPercent;
    private Double memoryTotal;
    private Double memoryUsed;
    private Double memoryFree;
    private Double temperature;

    public static GPU toModel(GPUDTORequest gpuDTORequest) {
        GPU gpu = new GPU();
        gpu.setGpuName(gpuDTORequest.getGpuName());
        gpu.setLoadPercent(gpuDTORequest.getLoadPercent());
        gpu.setMemoryTotal(gpuDTORequest.getMemoryTotal());
        gpu.setMemoryUsed(gpuDTORequest.getMemoryUsed());
        gpu.setMemoryFree(gpuDTORequest.getMemoryFree());
        gpu.setTemperature(gpuDTORequest.getTemperature());
        return gpu;
    }

}
