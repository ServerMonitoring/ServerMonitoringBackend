package com.example.back.controller;

import com.example.back.dto.request.BaseAndMetricSearchRequestDTO;
import com.example.back.dto.response.*;
import com.example.back.dto.search.*;
import com.example.back.service.*;
import com.example.back.util.ExtractCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metric")
public class MetricController {

    private final MetricService metricService;
    private final MemoryService memoryService;
    private final SwapService swapService;
    private final CPUService cpuService;
    private final NetworkConnectionService networkConnectionService;
    private final DiskService diskService;
    private final ServerService serverService;
    private final DiskIOService diskIOService;
    private final ExtractCriteria extractCriteria;


    @Autowired
    public MetricController(MetricService metricService, MemoryService memoryService, SwapService swapService, CPUService cpuService, NetworkConnectionService networkConnectionService, DiskService diskService, ServerService serverService, DiskIOService diskIOService, ExtractCriteria extractCriteria) {
        this.metricService = metricService;
        this.memoryService = memoryService;
        this.swapService = swapService;
        this.cpuService = cpuService;
        this.networkConnectionService = networkConnectionService;
        this.diskService = diskService;
        this.serverService = serverService;
        this.diskIOService = diskIOService;
        this.extractCriteria = extractCriteria;
    }

    @PostMapping()
    public ResponseEntity<List<MetricResponseDTO>> getMetric(@RequestBody(required = false) BaseAndMetricSearchRequestDTO requestDTO) {
        if (requestDTO == null) {
            requestDTO = new BaseAndMetricSearchRequestDTO();
        }
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<MetricResponseDTO> metrics = metricService.getMetricsByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(metrics);
    }

    @PostMapping("/static")
    public ResponseEntity<List<StaticMetricServerResponseDTO>> getStaticMetricServer(@RequestBody(required = false) ServerSearchCriteria criteria, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid authorization header");
        }

        if (criteria == null) {
            criteria = new ServerSearchCriteria();
        }
        String userToken = token.substring("Bearer ".length());

        List<StaticMetricServerResponseDTO> response = serverService.getStaticMetricServer(criteria, userToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<List<NetworkConnectionResponseDTO>> getNetworkConnections(@RequestBody(required = false) BaseAndMetricSearchRequestDTO requestDTO) {
        BaseSearchCriteria baseSearchCriteria = extractCriteria.extractBaseSearchCriteria(requestDTO);
        MetricTimeSearchCriteria metricTimeSearchCriteria = extractCriteria.extractMetricTimeSearchCriteria(requestDTO);

        List<NetworkConnectionResponseDTO> networkConnections = networkConnectionService.getNetworkConnectionsByCriteria(baseSearchCriteria, metricTimeSearchCriteria);

        return ResponseEntity.status(HttpStatus.OK).body(networkConnections);
    }

    @PostMapping("/disks")
    public ResponseEntity<Map<String, List<DiskResponseDTO>>> getDisks(@RequestBody(required = false) DiskSearchCriteria criteria) {
        if (criteria == null) {
            criteria = new DiskSearchCriteria();
        }
        Map<String, List<DiskResponseDTO>> disks = diskService.getDisksByCriteria(criteria);

        return ResponseEntity.status(HttpStatus.OK).body(disks);
    }
    @PostMapping("/disksIO")
    public ResponseEntity<Map<String, List<DiskIoResponseDTO>>> getDisksIO(@RequestBody(required = false) DiskIOSearchCriteria criteria) {
        if (criteria == null) {
            criteria = new DiskIOSearchCriteria();
        }
        Map<String, List<DiskIoResponseDTO>> diskIo = diskIOService.getDiskIoByCriteria(criteria);

        return ResponseEntity.status(HttpStatus.OK).body(diskIo);
    }
}
