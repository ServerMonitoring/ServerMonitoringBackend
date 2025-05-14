package com.example.back.dto.response;

import com.example.back.model.Metric;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MetricResponseDTO {
    private Long id;
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

    public static MetricResponseDTO toDTO(Metric metric) {
        return MetricResponseDTO.builder()
                .id(metric.getMetricId())
                .timestamp(metric.getTimestamp())
                .uptime(metric.getUptime())
                .netSent(metric.getNetSent())
                .netRecv(metric.getNetRecv())
                .netErrors(metric.getNetErrors())
                .netDrops(metric.getNetDrops())
                .failedLogins(metric.getFailedLogins())
                .activeConnections(metric.getActiveConnections())
                .diskTotalUsedPercent(metric.getDiskTotalUsedPercent())
                .diskTotalAvailable(metric.getDiskTotalAvailable())
                .build();
    }
}
