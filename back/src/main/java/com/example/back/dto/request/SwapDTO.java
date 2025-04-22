package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SwapDTO {
    private Long swapTotal;
    private Long swapUsed;
    private Long swapFree;
    private Double swapPercentUsed;

}
