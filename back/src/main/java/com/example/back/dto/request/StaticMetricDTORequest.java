package com.example.back.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StaticMetricDTORequest {
    @JsonProperty("hostname")
    private String hostname;
    @JsonProperty("os")
    private String os;
    @JsonProperty("cpu_model")
    private String cpuModel;
    @JsonProperty("cpu_count_cores")
    private Integer cpuCountCores;
    @JsonProperty("cpu_count_cores_physical")
    private Integer cpuCountCoresPhysical;
    @JsonProperty("min_freq")
    private Double minFreq;
    @JsonProperty("max_freq")
    private Double maxFreq;
}
