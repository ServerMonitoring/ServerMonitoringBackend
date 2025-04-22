package com.example.back.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CPUDTO {
    private Double cpuPercentTotalLoad;
    private Integer cpuCountCores;
    private Integer cpuCountPhysicalCores;
    private Double cpuTimeUser;
    private Double cpuTimeSystem;
    private Double cpuTimeIdle;
    private Long ctxSwitches;
    private Long interrupts;
    private Long softInterrupts;
    private Long syscalls;
    private Double currentFreq;
    private Double minFreq;
    private Double maxFreq;
}