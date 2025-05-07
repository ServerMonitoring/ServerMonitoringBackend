package com.example.back.dto.request;

import com.example.back.model.GPU;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GPUDTORequest {
    @JsonProperty("name")
    private String gpuName;
    @JsonProperty("load_percent")
    private Double loadPercent;
    @JsonProperty("memory_total")
    private Double memoryTotal;
    @JsonProperty("memory_used")
    private Double memoryUsed;
    @JsonProperty("memory_free")
    private Double memoryFree;
    @JsonProperty("memory_used_percent")
    private Double memoryUsedPercent;
    private Double temperature;

    public static GPU toModel(GPUDTORequest gpuDTORequest) {
        GPU gpu = new GPU();
        gpu.setGpuName(gpuDTORequest.getGpuName());
        gpu.setLoadPercent(gpuDTORequest.getLoadPercent());
        gpu.setMemoryTotal(gpuDTORequest.getMemoryTotal());
        gpu.setMemoryUsed(gpuDTORequest.getMemoryUsed());
        gpu.setMemoryFree(gpuDTORequest.getMemoryFree());
        gpu.setMemoryUsedPercent(gpuDTORequest.getMemoryUsedPercent());
        gpu.setTemperature(gpuDTORequest.getTemperature());
        return gpu;
    }

}
