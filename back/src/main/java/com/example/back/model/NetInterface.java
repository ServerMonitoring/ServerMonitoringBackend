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
    private Integer err_in;
    private Integer err_out;
    private Integer drop_in;
    private Integer drop_out;
}
