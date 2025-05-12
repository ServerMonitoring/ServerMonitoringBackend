package com.example.back.dto.search;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GPUSearchCriteria  {
    private BaseSearchCriteria baseCriteria = new BaseSearchCriteria();
    private MetricTimeSearchCriteria metricTimeCriteria = new MetricTimeSearchCriteria();
    private String gpuName;
}
