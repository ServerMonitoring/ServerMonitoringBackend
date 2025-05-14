package com.example.back.service;

import com.example.back.dto.response.GPUResponseDTO;
import com.example.back.dto.search.GPUSearchCriteria;

import java.util.List;
import java.util.Map;

public interface GPUService {
    Map<String, List<GPUResponseDTO>> getGPUsByCriteria(GPUSearchCriteria searchCriteria);
}
