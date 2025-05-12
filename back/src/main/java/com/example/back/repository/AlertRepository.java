package com.example.back.repository;

import com.example.back.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlertRepository extends JpaRepository<Alert, Integer>, JpaSpecificationExecutor<Alert> {
}
