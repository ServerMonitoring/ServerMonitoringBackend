package com.example.back.service.impl;

import com.example.back.dto.response.CPUResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.CPU;
import com.example.back.repository.CPURepository;
import com.example.back.service.CPUService;
import com.example.back.util.criteriaSpecification.SimpleMetricSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CPUServiceImpl implements CPUService {

    private final CPURepository cpuRepository;

    @Autowired
    public CPUServiceImpl(CPURepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    @Override
    public List<CPUResponseDTO> getCPUByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria){
        Specification<CPU> cpuSpecification = SimpleMetricSpecification.bySimpleCriteria(metricCriteria, baseCriteria);

        List<CPU> cpus = cpuRepository.findAll(cpuSpecification);

        return cpus.stream().map(CPUResponseDTO::toDTO).toList();
    }
}
