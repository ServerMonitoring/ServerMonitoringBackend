package com.example.back.service.impl;

import com.example.back.dto.response.DiskIoResponseDTO;

import com.example.back.dto.search.DiskIOSearchCriteria;
import com.example.back.model.DiskIO;
import com.example.back.repository.DiskIORepository;
import com.example.back.service.DiskIOService;
import com.example.back.util.criteriaSpecification.DiskIOSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiskIOServiceImpl implements DiskIOService {

    private final DiskIORepository DiskIORepository;

    @Autowired
    public DiskIOServiceImpl(com.example.back.repository.DiskIORepository diskIORepository) {
        DiskIORepository = diskIORepository;
    }

    @Override
    public Map<String, List<DiskIoResponseDTO>> getDiskIoByCriteria(DiskIOSearchCriteria searchCriteria) {
        Specification<DiskIO> diskIOSpecification = DiskIOSpecification.byCriteria(searchCriteria);
        List<DiskIO> diskIOList = DiskIORepository.findAll(diskIOSpecification);
        return diskIOList.stream()
                .map(DiskIoResponseDTO::toDTO)
                .collect(Collectors.groupingBy(DiskIoResponseDTO::getDriveName));
    }
}
