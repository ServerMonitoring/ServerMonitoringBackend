package com.example.back.controller;

import com.example.back.dto.request.MetricDTORequest;
import com.example.back.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/node")
public class NodeController {

    private final MetricService metricService;

    @Autowired
    public NodeController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping("/violite")
    public ResponseEntity<String> saveMetric(@RequestBody MetricDTORequest metricDTORequest) {
        metricService.saveMetrics(1L,metricDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
