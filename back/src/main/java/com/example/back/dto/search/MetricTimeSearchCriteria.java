package com.example.back.dto.search;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MetricTimeSearchCriteria {
    private Instant startTime;
    private Instant endTime;
    private Instant currentTime;
    private Long metricId;
}
