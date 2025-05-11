package com.example.back.util.alert;

import com.example.back.dto.request.MetricDTORequest;

import java.util.List;


@FunctionalInterface
public interface MetricExtractor {
    List<Double> extract(MetricDTORequest metricDTORequest);
}
