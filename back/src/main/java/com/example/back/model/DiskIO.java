package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter

public class DiskIO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diskIoId;

    @ManyToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private String driveName;
    private Long readCount;
    private Long writeCount;
    private Double read;
    private Double write;
}
