package com.example.back.dto.response;

import com.example.back.model.CPU;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CPUResponseDTO {
    private Long id;
    private Double cpuPercentTotalLoad;
    private Double cpuTimeUser;
    private Double cpuTimeSystem;
    private Double cpuTimeIdle;
    private Double cpuTimeInterrupt;
    private Double cpuTimeDpc;
    private Long ctxSwitches;
    private Long interrupts;
    private Long softInterrupts;
    private Long syscalls;
    private Double currentFreq;
    private List<CoresResponseDTO> cores;

    public static CPUResponseDTO toDTO(CPU cpu) {
        List<CoresResponseDTO> cores = cpu.getCores().stream().map(CoresResponseDTO::toDTO).toList();

        return CPUResponseDTO.builder()
                .id(cpu.getCpuId())
                .cpuPercentTotalLoad(cpu.getCpuPercentTotalLoad())
                .cpuTimeUser(cpu.getCpuTimeUser())
                .cpuTimeSystem(cpu.getCpuTimeSystem())
                .cpuTimeIdle(cpu.getCpuTimeIdle())
                .cpuTimeInterrupt(cpu.getCpuTimeInterrupt())
                .cpuTimeDpc(cpu.getCpuTimeDpc())
                .ctxSwitches(cpu.getCtxSwitches())
                .interrupts(cpu.getInterrupts())
                .softInterrupts(cpu.getSoftInterrupts())
                .syscalls(cpu.getSyscalls())
                .currentFreq(cpu.getCurrentFreq())
                .cores(cores)
                .build();
    }
}
