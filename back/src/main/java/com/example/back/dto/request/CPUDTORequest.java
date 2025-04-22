package com.example.back.dto.request;

import com.example.back.model.CPU;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CPUDTORequest {
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

    public static CPU toModel(CPUDTORequest cpuDTORequest) {
        CPU cpu = new CPU();
        cpu.setCpuPercentTotalLoad(cpuDTORequest.getCpuPercentTotalLoad());
        cpu.setCpuCountCores(cpuDTORequest.getCpuCountCores());
        cpu.setCpuCountPhysicalCores(cpuDTORequest.getCpuCountPhysicalCores());
        cpu.setCpuTimeUser(cpuDTORequest.getCpuTimeUser());
        cpu.setCpuTimeSystem(cpuDTORequest.getCpuTimeSystem());
        cpu.setCpuTimeIdle(cpuDTORequest.getCpuTimeIdle());
        cpu.setCtxSwitches(cpuDTORequest.getCtxSwitches());
        cpu.setInterrupts(cpuDTORequest.getInterrupts());
        cpu.setSoftInterrupts(cpuDTORequest.getSoftInterrupts());
        cpu.setSyscalls(cpuDTORequest.getSyscalls());
        cpu.setCurrentFreq(cpuDTORequest.getCurrentFreq());
        cpu.setMinFreq(cpuDTORequest.getMinFreq());
        cpu.setMaxFreq(cpuDTORequest.getMaxFreq());
        return cpu;
    }
}