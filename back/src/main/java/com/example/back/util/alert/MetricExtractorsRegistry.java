package com.example.back.util.alert;

import com.example.back.dto.request.*;
import com.example.back.model.enums.MetricKey;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Component
public class MetricExtractorsRegistry {

    private final Map<MetricKey, MetricExtractor> extractors = new HashMap<>();

    public MetricExtractorsRegistry() {

        extractors.put(MetricKey.CPU_TOTAL_LOAD, metric ->
                Optional.ofNullable(metric.getCpu())
                        .map(cpu -> List.of(cpu.getCpuPercentTotalLoad()))
                        .orElse(List.of()));

        extractors.put(MetricKey.MEMORY_PERCENT, metric ->
                Optional.ofNullable(metric.getMemory())
                        .map(mem -> List.of(mem.getMemoryUsedPercent()))
                        .orElse(List.of()));

        extractors.put(MetricKey.SWAP_PERCENT, metric ->
                Optional.ofNullable(metric.getSwap())
                        .map(swap -> List.of(swap.getSwapPercentUsed()))
                        .orElse(List.of()));

        extractors.put(MetricKey.DISK_USED_PERCENT, metric ->
                Optional.ofNullable(metric.getDisks())
                        .orElse(List.of()).stream()
                        .map(DiskDTORequest::getDiskUsedPercent)
                        .filter(Objects::nonNull)
                        .toList());

        extractors.put(MetricKey.GPU_LOAD, metric ->
                Optional.ofNullable(metric.getGpu())
                        .orElse(List.of()).stream()
                        .map(GPUDTORequest::getLoadPercent)
                        .filter(Objects::nonNull)
                        .toList());

        extractors.put(MetricKey.GPU_MEMORY_USED_PERCENT, metric ->
                Optional.ofNullable(metric.getGpu())
                        .orElse(List.of()).stream()
                        .map(GPUDTORequest::getMemoryUsedPercent)
                        .filter(Objects::nonNull)
                        .toList());
    }

    public List<Double> extractValues(MetricKey key, MetricDTORequest metric) {
        return Optional.ofNullable(extractors.get(key))
                .map(extractor -> extractor.extract(metric))
                .orElse(List.of());
    }
}
