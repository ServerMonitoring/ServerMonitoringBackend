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
    private String ipAddress;
    private String addInfo;
    private boolean online;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Metric> metrics;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Log> logs;
}
