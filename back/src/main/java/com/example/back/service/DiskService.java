package com.example.back.service;

import com.example.back.dto.response.DiskResponseDTO;
import com.example.back.dto.search.DiskSearchCriteria;

import java.util.List;
import java.util.Map;

public interface DiskService {
    Map<String, List<DiskResponseDTO>> getDisksByCriteria(DiskSearchCriteria diskSearchCriteria);
}
