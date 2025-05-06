package com.example.back.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CoresDTORequest {
    @JsonProperty("core_index")
    private Integer coreIndex;
    @JsonProperty("core_percent_load")
    private Double corePercentLoad;

}
