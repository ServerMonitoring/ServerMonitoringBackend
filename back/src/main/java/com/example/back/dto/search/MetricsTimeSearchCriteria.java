package com.example.back.dto.search;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MetricsTimeSearchCriteria {
    private Instant startTime;
    private Instant endTime;
}
