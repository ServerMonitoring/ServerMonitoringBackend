package com.example.back.dto.response;

import com.example.back.model.Core;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CoresResponseDTO {
    private Integer coreIndex;
    private Double corePercentLoad;

    public static CoresResponseDTO toDTO(Core core){

        return CoresResponseDTO.builder()
                .coreIndex(core.getCoreIndex())
                .corePercentLoad(core.getCorePercentLoad())
                .build();
    }

}
