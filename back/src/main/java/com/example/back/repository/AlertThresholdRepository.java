package com.example.back.repository;

import com.example.back.model.AlertThreshold;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertThresholdRepository extends JpaRepository<AlertThreshold, Long> {
    List<AlertThreshold> findAllByServer_ServerId(Long serverId);
}
