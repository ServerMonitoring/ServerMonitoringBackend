package com.example.back.controller;

import com.example.back.dto.request.MetricDTORequest;
import com.example.back.dto.request.StaticMetricDTORequest;
import com.example.back.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/node")
public class NodeController {

    private final MetricService metricService;
    private static final String HEADER_NAME = "X-API-Key";

    @Autowired
    public NodeController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping("/changeable")
    public ResponseEntity<String> saveMetric(@RequestBody MetricDTORequest metricDTORequest,@RequestHeader(value = HEADER_NAME) String token) {

        metricService.saveMetrics(token,metricDTORequest);
        return ResponseEntity.status(HttpStatus.OK).body("success save changeable metric");
    }

    @PostMapping("/static")
    public ResponseEntity<String> saveStaticMetric(@RequestBody StaticMetricDTORequest staticMetricDTORequest,@RequestHeader(value = HEADER_NAME) String token) {

        metricService.saveStaticMetrics(token,staticMetricDTORequest);
        return ResponseEntity.status(HttpStatus.OK).body("success save static metric");
    }
}
