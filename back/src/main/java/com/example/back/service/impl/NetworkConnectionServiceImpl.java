package com.example.back.service.impl;

import com.example.back.dto.response.NetworkConnectionResponseDTO;
import com.example.back.dto.search.BaseSearchCriteria;
import com.example.back.dto.search.MetricTimeSearchCriteria;
import com.example.back.model.NetworkConnection;
import com.example.back.repository.NetworkConnectionRepository;
import com.example.back.service.NetworkConnectionService;
import com.example.back.util.criteriaSpecification.SimpleMetricSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NetworkConnectionServiceImpl implements NetworkConnectionService {

    private final NetworkConnectionRepository networkConnectionRepository;

    @Autowired
    public NetworkConnectionServiceImpl(NetworkConnectionRepository networkConnectionRepository) {
        this.networkConnectionRepository = networkConnectionRepository;
    }

    @Override
    public List<NetworkConnectionResponseDTO> getNetworkConnectionsByCriteria(BaseSearchCriteria baseCriteria, MetricTimeSearchCriteria metricCriteria) {
        Specification<NetworkConnection> networkConnectionSpecification = SimpleMetricSpecification.bySimpleCriteria(metricCriteria, baseCriteria);
        List<NetworkConnection> networkConnections = networkConnectionRepository.findAll(networkConnectionSpecification);
        return networkConnections.stream().map(NetworkConnectionResponseDTO::toDTO).toList();
    }
}
