package com.example.back.model;

import com.example.back.model.enums.AlertLevel;
import com.example.back.model.enums.MetricKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class AlertThreshold {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thresholdId;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    @Enumerated(EnumType.STRING)
    private MetricKey metricKey;

    @Enumerated(EnumType.STRING)
    private AlertLevel alertLevel;

    private double threshold;

    @OneToMany(mappedBy = "threshold", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alert> alerts;
}
