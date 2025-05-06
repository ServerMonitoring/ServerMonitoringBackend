package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class Disk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diskId;

    @ManyToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private String device;
    private String mountpoint;
    private Long diskTotal;
    private Long diskUsed;
    private Long diskFree;
    private Double diskUsedPercent;
}

