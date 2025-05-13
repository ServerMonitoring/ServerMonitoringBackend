package com.example.back.service;

import com.example.back.dto.response.MemoryResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;

import java.util.List;

public interface MemoryService {
    List<MemoryResponseDTO> getMemoryByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria);
}
