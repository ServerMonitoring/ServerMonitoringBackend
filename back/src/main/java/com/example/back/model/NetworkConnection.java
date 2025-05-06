package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter

public class NetworkConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long networkConnectionId;

    @OneToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private Integer tcp;
    private Integer udp;
}
