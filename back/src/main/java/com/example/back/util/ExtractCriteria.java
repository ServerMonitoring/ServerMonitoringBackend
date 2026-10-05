package com.example.back.util;

import com.example.back.dto.request.BaseAndMetricSearchRequestDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ExtractCriteria {
    private final long defaultQueryWindowSeconds;

    public ExtractCriteria(
            @Value("${monitoring.metrics.default-query-window-seconds:300}") long defaultQueryWindowSeconds
    ) {
        if (defaultQueryWindowSeconds <= 0) {
            throw new IllegalArgumentException("Default metric query window must be positive");
        }
        this.defaultQueryWindowSeconds = defaultQueryWindowSeconds;
    }

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
        return applyDefaultTimeWindow(metricCriteria);
    }

    public MetricTimeSearchCriteria applyDefaultTimeWindow(MetricTimeSearchCriteria criteria) {
        if (criteria == null) {
            criteria = new MetricTimeSearchCriteria();
        }

        if (criteria.getCurrentTime() == null && criteria.getMetricId() == null) {
            Instant endTime = criteria.getEndTime() == null ? Instant.now() : criteria.getEndTime();
            Instant startTime = criteria.getStartTime() == null
                    ? endTime.minusSeconds(defaultQueryWindowSeconds)
                    : criteria.getStartTime();
            criteria.setStartTime(startTime);
            criteria.setEndTime(endTime);
        }

        return criteria;
    }
}
