package com.example.back.service;

import com.example.back.dto.response.SwapResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;

import java.util.List;

public interface SwapService {
    List<SwapResponseDTO> getSwapByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria);
}
