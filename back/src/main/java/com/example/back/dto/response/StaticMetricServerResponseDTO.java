package com.example.back.dto.response;

import com.example.back.model.Server;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StaticMetricServerResponseDTO {
    private String cpuModel;
    private Integer cpuCountCores;
    private Integer cpuCountCoresPhysical;
    private Double minFreq;
    private Double maxFreq;

    public static StaticMetricServerResponseDTO toDTO(Server server){
        return StaticMetricServerResponseDTO.builder()
                .cpuModel(server.getCpuModel())
                .cpuCountCores(server.getCpuCountCores())
                .cpuCountCoresPhysical(server.getCpuCountCoresPhysical())
                .minFreq(server.getMinFreq())
                .maxFreq(server.getMaxFreq())
                .build();
    }
}
