package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class Swap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long swapId;

    @OneToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private Long swapTotal;
    private Long swapUsed;
    private Long swapFree;
    private Double swapPercentUsed;
}
