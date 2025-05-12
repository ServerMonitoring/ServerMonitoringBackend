package com.example.back.repository;

import com.example.back.model.CPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CPURepository extends JpaRepository<CPU, Long>, JpaSpecificationExecutor<CPU> {
}
