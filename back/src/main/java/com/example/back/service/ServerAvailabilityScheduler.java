package com.example.back.service;

import com.example.back.repository.ServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ServerAvailabilityScheduler {
    private static final Logger logger = LoggerFactory.getLogger(ServerAvailabilityScheduler.class);

    private final ServerRepository serverRepository;
    private final long timeoutSeconds;

    public ServerAvailabilityScheduler(
            ServerRepository serverRepository,
            @Value("${monitoring.online.timeout-seconds:65}") long timeoutSeconds
    ) {
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("Server online timeout must be positive");
        }
        this.serverRepository = serverRepository;
        this.timeoutSeconds = timeoutSeconds;
    }

    @Scheduled(fixedDelayString = "${monitoring.online.check-period-ms:10000}")
    public void markServersOfflineWhenStale() {
        Instant cutoff = Instant.now().minusSeconds(timeoutSeconds);
        int updatedServers = serverRepository.markOfflineIfLastSeenBefore(cutoff);
        if (updatedServers > 0) {
            logger.info("Marked {} server(s) offline because their heartbeat timed out", updatedServers);
        }
    }
}
