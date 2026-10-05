package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_server_online_last_seen", columnList = "online,last_seen_at"))
@Getter
@Setter
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serverId;

    @ManyToOne
    @JoinColumn(name = "usersId")
    private Users users;

    private String hostname;
    private String osInfo;
    private String address;
    private String serverName;
    private String addInfo;
    private Boolean online;
    @Column(name = "last_seen_at")
    private Instant lastSeenAt;
    private Integer nodeTokenVersion = 0;
    private String cpuModel;
    private Integer cpuCountCores;
    private Integer cpuCountCoresPhysical;
    private Double minFreq;
    private Double maxFreq;


    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metric> metrics;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Log> logs;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlertThreshold> alertThresholds;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alert> alerts;

}
