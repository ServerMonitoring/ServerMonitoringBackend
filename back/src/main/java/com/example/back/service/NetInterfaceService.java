package com.example.back.service;

import com.example.back.dto.response.NetInterfaceResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;

import java.util.List;

public interface NetInterfaceService {
    List<NetInterfaceResponseDTO> getNetInterfacesByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria);
}
