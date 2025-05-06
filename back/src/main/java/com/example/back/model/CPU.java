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

    @OneToMany(mappedBy = "cpu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Core> cores;
}
