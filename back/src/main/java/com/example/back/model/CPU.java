package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter

public class CPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpuId;

    @OneToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

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

    @OneToMany(mappedBy = "cpu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Core> cores;
}
