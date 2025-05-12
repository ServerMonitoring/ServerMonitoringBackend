package com.example.back.dto.request;

import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseAndMetricSearchRequestDTO {
    private BaseSearchCriteria baseCriteria;
    private MetricTimeSearchCriteria metricTimeCriteria;
}
