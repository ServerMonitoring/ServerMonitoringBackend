package com.example.back.service;

import com.example.back.dto.response.NetworkConnectionResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;

import java.util.List;

public interface NetworkConnectionService {
    List<NetworkConnectionResponseDTO> getNetworkConnectionsByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria);
}
