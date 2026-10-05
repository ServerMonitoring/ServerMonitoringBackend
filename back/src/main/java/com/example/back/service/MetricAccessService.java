package com.example.back.service;

import com.example.back.config.security.components.CustomUserDetails;
import com.example.back.exception.ResourceAccessDeniedException;
import com.example.back.exception.UserNotFoundException;
import com.example.back.model.Server;
import com.example.back.repository.ServerRepository;
import com.example.back.repository.MetricRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MetricAccessService {
    private final ServerRepository serverRepository;
    private final MetricRepository metricRepository;

    public MetricAccessService(ServerRepository serverRepository, MetricRepository metricRepository) {
        this.serverRepository = serverRepository;
        this.metricRepository = metricRepository;
    }

    public void assertCanReadMetric(Long metricId) {
        if (metricId == null) {
            return;
        }

        CustomUserDetails user = currentUser();
        Server server = metricRepository.findById(metricId)
                .map(metric -> metric.getServer())
                .orElseThrow(() -> new UserNotFoundException("Metric not found"));

        if (!user.isAdmin() && !user.getId().equals(server.getUsers().getUserId())) {
            throw new ResourceAccessDeniedException("You do not have access to metrics of this server");
        }
    }

    public void assertCanReadServer(Long serverId) {
        if (serverId == null) {
            return;
        }

        CustomUserDetails user = currentUser();
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new UserNotFoundException("Server not found"));

        if (!user.isAdmin() && !user.getId().equals(server.getUsers().getUserId())) {
            throw new ResourceAccessDeniedException("You do not have access to metrics of this server");
        }
    }

    private CustomUserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof CustomUserDetails user)) {
            throw new ResourceAccessDeniedException("User authentication is required to read metric history");
        }
        return user;
    }
}
