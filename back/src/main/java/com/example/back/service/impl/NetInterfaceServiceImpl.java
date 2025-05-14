package com.example.back.service.impl;

import com.example.back.dto.response.NetInterfaceResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.NetInterface;
import com.example.back.repository.NetInterfaceRepository;
import com.example.back.service.NetInterfaceService;
import com.example.back.util.criteriaSpecification.SimpleMetricSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetInterfaceServiceImpl implements NetInterfaceService {

    private final NetInterfaceRepository netInterfaceRepository;

    @Autowired
    public NetInterfaceServiceImpl(NetInterfaceRepository netInterfaceRepository) {
        this.netInterfaceRepository = netInterfaceRepository;
    }

    @Override
    public List<NetInterfaceResponseDTO> getNetInterfacesByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria) {
        Specification<NetInterface> netInterfaceSpecification = SimpleMetricSpecification.bySimpleCriteria(metricCriteria, baseCriteria);
        List<NetInterface> netInterfaces = netInterfaceRepository.findAll(netInterfaceSpecification);
        return netInterfaces.stream().map(NetInterfaceResponseDTO::toDTO).toList();
    }
}
