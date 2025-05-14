package com.example.back.service.impl;

import com.example.back.dto.response.SwapResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.Swap;
import com.example.back.repository.SwapRepository;
import com.example.back.service.SwapService;
import com.example.back.util.criteriaSpecification.SimpleMetricSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwapServiceImpl implements SwapService {

    private final SwapRepository swapRepository;

    public SwapServiceImpl(SwapRepository swapRepository) {
        this.swapRepository = swapRepository;
    }

    @Override
    public List<SwapResponseDTO> getSwapByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria){
        Specification<Swap> swapSpecification = SimpleMetricSpecification.bySimpleCriteria(metricCriteria, baseCriteria);
        List<Swap> swaps = swapRepository.findAll(swapSpecification);
        return swaps.stream().map(SwapResponseDTO::toDTO).toList();
    }
}
