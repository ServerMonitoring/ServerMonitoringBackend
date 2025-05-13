package com.example.back.controller;

import com.example.back.dto.request.BaseAndMetricSearchRequestDTO;
import com.example.back.dto.response.*;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.service.*;
import com.example.back.util.ExtractCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metric")
public class MetricController {

    private final MetricService metricService;
    private final MemoryService memoryService;
    private final SwapService swapService;
    private final CPUService cpuService;
    private final NetworkConnectionServce networkConnectionService;
    private final ExtractCriteria extractCriteria;


    @Autowired
    public MetricController(MetricService metricService, MemoryService memoryService, SwapService swapService, CPUService cpuService, NetworkConnectionServce networkConnectionService, ExtractCriteria extractCriteria) {
        this.metricService = metricService;
        this.memoryService = memoryService;
        this.swapService = swapService;
        this.cpuService = cpuService;
        this.networkConnectionService = networkConnectionService;
        this.extractCriteria = extractCriteria;
    }

    @PostMapping()
    public ResponseEntity<List<MetricResponseDTO>> getMetric(@RequestBody(required = false) BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<MetricResponseDTO> metrics = metricService.getMetricsByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(metrics);
    }

    @PostMapping("/memory")
    public ResponseEntity<List<MemoryResponseDTO>> getMemory(@RequestBody(required = false) BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<MemoryResponseDTO> memory = memoryService.getMemoryByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(memory);
    }

    @PostMapping("/swap")
    public ResponseEntity<List<SwapResponseDTO>> getSwap(@RequestBody(required = false) BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<SwapResponseDTO> swaps = swapService.getSwapByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(swaps);
    }

    @PostMapping("/cpu")
    public ResponseEntity<List<CPUResponseDTO>> getCPU(@RequestBody(required = false) BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<CPUResponseDTO> cpus = cpuService.getCPUByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(cpus);
    }

    @PostMapping("/network_connection")
    public ResponseEntity<List<NetworkConnectionResponseDTO>> getNetworkConnections(@RequestBody BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<NetworkConnectionResponseDTO> networkConnections = networkConnectionService.getNetworkConnectionsByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(networkConnections);
    }
}
