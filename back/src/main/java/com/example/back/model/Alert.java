package com.example.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    @OneToOne
    @JoinColumn(name = "threshold_id")
    private AlertThreshold threshold;

    private String alertMessage;

    private Instant timestamp = Instant.now();
}
