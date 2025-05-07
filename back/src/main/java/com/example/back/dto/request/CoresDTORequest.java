package com.example.back.dto.request;

import com.example.back.model.Core;
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


    public static Core toModel(CoresDTORequest coresDTORequest) {
        Core core = new Core();
        core.setCoreIndex(coresDTORequest.getCoreIndex());
        core.setCorePercentLoad(coresDTORequest.getCorePercentLoad());
        return core;
    }
}
