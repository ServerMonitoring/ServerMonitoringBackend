package com.example.back.service;

import com.example.back.dto.request.MetricDTORequest;
import com.example.back.dto.request.StaticMetricDTORequest;
import com.example.back.dto.response.MetricResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MetricService {
    List<MetricResponseDTO> getMetricsByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria);

    @Transactional
    void saveStaticMetrics(String nodeToken, StaticMetricDTORequest dtoRequest);

    @Transactional
    void saveMetrics(String nodeToken, MetricDTORequest metricDTORequest);
}
