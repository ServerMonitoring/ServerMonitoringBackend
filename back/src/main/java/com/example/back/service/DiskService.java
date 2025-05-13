package com.example.back.service;

import com.example.back.dto.response.DiskResponseDTO;
import com.example.back.dto.search.DiskSearchCriteria;

import java.util.List;

public interface DiskService {
    List<DiskResponseDTO> getDisksByCriteria(DiskSearchCriteria diskSearchCriteria);
}
