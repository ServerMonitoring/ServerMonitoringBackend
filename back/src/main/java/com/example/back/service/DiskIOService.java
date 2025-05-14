package com.example.back.service;

import com.example.back.dto.response.DiskIoResponseDTO;
import com.example.back.dto.search.DiskIOSearchCriteria;

import java.util.List;
import java.util.Map;

public interface DiskIOService {
    Map<String, List<DiskIoResponseDTO>> getDiskIoByCriteria(DiskIOSearchCriteria searchCriteria);
}
