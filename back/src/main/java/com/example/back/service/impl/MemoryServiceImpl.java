package com.example.back.service.impl;

import com.example.back.dto.response.MemoryResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.Memory;
import com.example.back.repository.MemoryRepository;
import com.example.back.service.MemoryService;
import com.example.back.util.criteriaSpecification.SimpleMetricSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryServiceImpl implements MemoryService {

    private final MemoryRepository memoryRepository;

    @Autowired
    public MemoryServiceImpl(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public List<MemoryResponseDTO> getMemoryByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria){
        Specification<Memory> specification = SimpleMetricSpecification.bySimpleCriteria(metricCriteria, baseCriteria);
        List<Memory> memoryList = memoryRepository.findAll(specification);
        return memoryList.stream().map(MemoryResponseDTO::toDTO).toList();
    }
}
