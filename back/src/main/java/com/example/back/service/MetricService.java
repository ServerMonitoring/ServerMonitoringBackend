package com.example.back.service;

import com.example.back.dto.request.MetricDTORequest;
import com.example.back.dto.request.StaticMetricDTORequest;
import org.springframework.transaction.annotation.Transactional;

public interface MetricService {
    @Transactional
    void saveStaticMetrics(String nodeToken, StaticMetricDTORequest dtoRequest);

    @Transactional
    void saveMetrics(String nodeToken, MetricDTORequest metricDTORequest);
}
