package com.example.back.repository;

import com.example.back.model.AlertThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AlertThresholdRepository extends JpaRepository<AlertThreshold, Long>, JpaSpecificationExecutor<AlertThreshold> {
    List<AlertThreshold> findAllByServer_ServerId(Long serverId);
}
