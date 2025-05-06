package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter

public class Core {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coreId;

    @ManyToOne
    @JoinColumn(name = "cpuId")
    private CPU cpu;

    private Integer coreIndex;
    private Double corePercentLoad;
}
