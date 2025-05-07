package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class GPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpuId;

    @ManyToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private String gpuName;
    private Double loadPercent;
    private Double memoryTotal;
    private Double memoryUsed;
    private Double memoryFree;
    private Double memoryUsedPercent;
    private Double temperature;
}