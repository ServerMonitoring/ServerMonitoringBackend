package com.example.back.dto.request;

import com.example.back.model.CPU;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CPUDTORequest {
    @JsonProperty("cpu_percent_total_load")
    private Double cpuPercentTotalLoad;
    @JsonProperty("cpu_time_user")
    private Double cpuTimeUser;
    @JsonProperty("cpu_time_system")
    private Double cpuTimeSystem;
    @JsonProperty("cpu_time_idle")
    private Double cpuTimeIdle;
    @JsonProperty("ctx_switches")
    private Long ctxSwitches;
    private Long interrupts;
    @JsonProperty("soft_interrupts")
    private Long softInterrupts;
    private Long syscalls;
    @JsonProperty("current_freq")
    private Double currentFreq;
    private List<CoresDTORequest> cores;

    public static CPU toModel(CPUDTORequest cpuDTORequest) {
        CPU cpu = new CPU();
        cpu.setCpuPercentTotalLoad(cpuDTORequest.getCpuPercentTotalLoad());
        cpu.setCpuTimeUser(cpuDTORequest.getCpuTimeUser());
        cpu.setCpuTimeSystem(cpuDTORequest.getCpuTimeSystem());
        cpu.setCpuTimeIdle(cpuDTORequest.getCpuTimeIdle());
        cpu.setCtxSwitches(cpuDTORequest.getCtxSwitches());
        cpu.setInterrupts(cpuDTORequest.getInterrupts());
        cpu.setSoftInterrupts(cpuDTORequest.getSoftInterrupts());
        cpu.setSyscalls(cpuDTORequest.getSyscalls());
        cpu.setCurrentFreq(cpuDTORequest.getCurrentFreq());
        return cpu;
    }
}