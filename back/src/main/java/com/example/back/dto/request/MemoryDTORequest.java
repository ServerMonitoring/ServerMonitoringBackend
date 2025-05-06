package com.example.back.dto.request;

import com.example.back.model.Memory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemoryDTORequest {
    @JsonProperty("total")
    private Long memoryTotal;
    @JsonProperty("used")
    private Long memoryUsed;
    @JsonProperty("free")
    private Long memoryFree;
    @JsonProperty("cached")
    private Long memoryCached;
    @JsonProperty("percent")
    private Double memoryUsedPercent;

    public static Memory toModel(MemoryDTORequest memoryDTO) {

        Memory memory = new Memory();
        memory.setMemoryTotal(memoryDTO.getMemoryTotal());
        memory.setMemoryUsed(memoryDTO.getMemoryUsed());
        memory.setMemoryFree(memoryDTO.getMemoryFree());
        memory.setMemoryCached(memoryDTO.getMemoryCached());
        memory.setMemoryUsedPercent(memoryDTO.getMemoryUsedPercent());
        return memory;
    }

}
