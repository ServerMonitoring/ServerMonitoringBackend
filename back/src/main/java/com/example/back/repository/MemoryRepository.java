package com.example.back.repository;

import com.example.back.model.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemoryRepository extends JpaRepository<Memory, Long>, JpaSpecificationExecutor<Memory> {
}
