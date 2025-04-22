package com.example.back.dto.request;

import com.example.back.model.Swap;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SwapDTORequest {
    private Long swapTotal;
    private Long swapUsed;
    private Long swapFree;
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
