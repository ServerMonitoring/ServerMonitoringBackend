package com.example.back.service;

import com.example.back.dto.request.MetricDTORequest;
import org.springframework.transaction.annotation.Transactional;

public interface MetricService {
    @Transactional
    void saveMetrics(Long serverId, MetricDTORequest metricDTORequest);
}
