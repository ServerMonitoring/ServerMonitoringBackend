package com.example.back.dto.response;

import com.example.back.model.Swap;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SwapResponseDTO {
    private Long id;
    private Long swapTotal;
    private Long swapUsed;
    private Long swapFree;
    private Double swapPercentUsed;

    public static SwapResponseDTO toDTO(Swap swap) {
        return SwapResponseDTO.builder()
                .id(swap.getSwapId())
                .swapTotal(swap.getSwapTotal())
                .swapUsed(swap.getSwapUsed())
                .swapFree(swap.getSwapFree())
                .swapPercentUsed(swap.getSwapPercentUsed())
                .build();
    }
}
