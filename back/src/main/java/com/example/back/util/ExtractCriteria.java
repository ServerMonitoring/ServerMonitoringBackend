package com.example.back.util;

import com.example.back.dto.request.BaseAndMetricSearchRequestDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import org.springframework.stereotype.Component;

@Component
public class ExtractCriteria {

    public BaseSearchCriteria extractBaseSearchCriteria (BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseCriteria =
                requestDTO == null || requestDTO.getBaseCriteria() == null
                        ? new BaseSearchCriteria()
                        : requestDTO.getBaseCriteria();
        return  baseCriteria;
    }

    public MetricTimeSearchCriteria extractMetricTimeSearchCriteria (BaseAndMetricSearchRequestDTO requestDTO) {
        MetricTimeSearchCriteria metricCriteria =
                requestDTO == null || requestDTO.getMetricTimeCriteria() == null
                        ? new MetricTimeSearchCriteria()
                        : requestDTO.getMetricTimeCriteria();
        return  metricCriteria;
    }
}
