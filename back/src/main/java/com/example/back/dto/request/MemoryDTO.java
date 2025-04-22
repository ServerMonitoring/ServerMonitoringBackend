package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemoryDTO {
    private Long memoryTotal;
    private Long memoryUsed;
    private Long memoryFree;
    private Long memoryCached;
    private Double memoryUsedPercent;

}
