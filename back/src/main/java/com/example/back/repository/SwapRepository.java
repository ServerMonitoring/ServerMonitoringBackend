package com.example.back.repository;

import com.example.back.model.Swap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SwapRepository extends JpaRepository<Swap, Long>, JpaSpecificationExecutor<Swap> {
}
