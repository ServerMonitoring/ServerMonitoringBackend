package com.example.back.service;

import com.example.back.dto.response.CPUResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;

import java.util.List;

public interface CPUService {
    List<CPUResponseDTO> getCPUByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria);
}
