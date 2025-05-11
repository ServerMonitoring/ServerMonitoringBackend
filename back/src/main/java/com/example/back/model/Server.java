package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
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
    private String addInfo;
    private boolean online;
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
