package com.example.back.repository;

import com.example.back.model.GPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GPURepository extends JpaRepository<GPU, Long>, JpaSpecificationExecutor<GPU> {
}
