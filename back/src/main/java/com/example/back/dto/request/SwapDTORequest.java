package com.example.back.dto.request;

import com.example.back.model.Swap;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SwapDTORequest {
    @JsonProperty("total")
    private Long swapTotal;
    @JsonProperty("used")
    private Long swapUsed;
    @JsonProperty("free")
    private Long swapFree;
    @JsonProperty("percent")
    private Double swapPercentUsed;


    public static Swap toModel(SwapDTORequest swapDTO) {
        Swap swap = new Swap();
        swap.setSwapTotal(swapDTO.getSwapTotal());
        swap.setSwapUsed(swapDTO.getSwapUsed());
        swap.setSwapFree(swapDTO.getSwapFree());
        swap.setSwapPercentUsed(swapDTO.getSwapPercentUsed());
        return swap;
    }
}
