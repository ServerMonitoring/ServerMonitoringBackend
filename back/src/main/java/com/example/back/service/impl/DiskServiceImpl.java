package com.example.back.service.impl;

import com.example.back.dto.response.DiskResponseDTO;
import com.example.back.dto.search.DiskSearchCriteria;
import com.example.back.model.Disk;
import com.example.back.repository.DiskRepository;
import com.example.back.service.DiskService;
import com.example.back.util.criteriaSpecification.DiskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiskServiceImpl implements DiskService {

    private final DiskRepository diskRepository;

    @Autowired
    public DiskServiceImpl(DiskRepository diskRepository) {
        this.diskRepository = diskRepository;
    }

    @Override
    public Map<String, List<DiskResponseDTO>> getDisksByCriteria(DiskSearchCriteria searchCriteria) {
        Specification<Disk> diskSpecification = DiskSpecification.byCriteria(searchCriteria);
        List<Disk> disks = diskRepository.findAll(diskSpecification);

        return disks.stream()
                .map(DiskResponseDTO::toDTO)
                .collect(Collectors.groupingBy(DiskResponseDTO::getDevice));
    }
}
