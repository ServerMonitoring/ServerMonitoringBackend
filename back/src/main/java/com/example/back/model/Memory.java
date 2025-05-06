package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoryId;

    @OneToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private Long memoryTotal;
    private Long memoryUsed;
    private Long memoryFree;
    private Long memoryCached;
    private Double memoryUsedPercent;
}
