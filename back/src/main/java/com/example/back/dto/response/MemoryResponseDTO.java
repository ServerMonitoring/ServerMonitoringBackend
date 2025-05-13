package com.example.back.dto.response;

import com.example.back.model.Memory;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemoryResponseDTO {
    private Long id;
    private Long memoryTotal;
    private Long memoryUsed;
    private Long memoryFree;
    private Long memoryCached;
    private Double memoryUsedPercent;

    public static MemoryResponseDTO toDTO(Memory memory) {
        return MemoryResponseDTO.builder()
                .id(memory.getMemoryId())
                .memoryTotal(memory.getMemoryTotal())
                .memoryUsed(memory.getMemoryUsed())
                .memoryFree(memory.getMemoryFree())
                .memoryCached(memory.getMemoryCached())
                .memoryUsedPercent(memory.getMemoryUsedPercent())
                .build();
    }
}
