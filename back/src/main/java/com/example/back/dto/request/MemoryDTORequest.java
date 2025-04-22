package com.example.back.dto.request;

import com.example.back.model.Memory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemoryDTORequest {
    private Long memoryTotal;
    private Long memoryUsed;
    private Long memoryFree;
    private Long memoryCached;
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
