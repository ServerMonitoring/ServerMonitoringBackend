package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class NetInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long netInterfaceId;

    @ManyToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    private String interfaceName;
    private Double sent;
    private Double recv;
    private Double packetsSent;
    private Double packetsRecv;
    private Integer errIn;
    private Integer errOut;
    private Integer dropIn;
    private Integer dropOut;
}
