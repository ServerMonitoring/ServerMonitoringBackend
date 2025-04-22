package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @ManyToOne
    @JoinColumn(name = "serverId")
    private Server server;

    private Instant timestamp;
    private Long uptime;
    private Double netSent;
    private Double netRecv;
    private Integer netErrors;
    private Integer netDrops;
    private Integer failedLogins;
    private Integer activeConnections;
    private Double diskTotalUsedPercent;
    private Double diskTotalAvailable;

    @OneToOne(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private Memory memory;

    @OneToOne(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private Swap swap;

    @OneToOne(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private CPU cpu;

    @OneToOne(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private NetworkConnection networkConnection;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disk> disks;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiskIO> diskIo;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GPU> gpu;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NetInterface> netInterfaces;
}
