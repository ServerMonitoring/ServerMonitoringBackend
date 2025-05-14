package com.example.back.service.impl;


import com.example.back.dto.response.GPUResponseDTO;
import com.example.back.dto.search.GPUSearchCriteria;
import com.example.back.model.GPU;
import com.example.back.repository.GPURepository;
import com.example.back.service.GPUService;
import com.example.back.util.criteriaSpecification.GPUSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GPUServiceImpl implements GPUService {

    private final GPURepository GPURepository;

    @Autowired
    public GPUServiceImpl(com.example.back.repository.GPURepository gpuRepository) {
        GPURepository = gpuRepository;
    }

    @Override
    public Map<String, List<GPUResponseDTO>> getGPUsByCriteria(GPUSearchCriteria searchCriteria) {
        Specification<GPU> gpuSpecification = GPUSpecification.byCriteria(searchCriteria);
        List<GPU> GPUList = GPURepository.findAll(gpuSpecification);

        return GPUList.stream()
                .map(GPUResponseDTO::toDTO)
                .collect(Collectors.groupingBy(GPUResponseDTO::getGpuName));
    }
}
