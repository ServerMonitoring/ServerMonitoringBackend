package com.example.back.repository;

import com.example.back.model.Core;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoreRepository extends JpaRepository<Core, Long>, JpaSpecificationExecutor<Core> {
}
