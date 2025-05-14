package com.example.back.repository;

import com.example.back.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LogRepository extends JpaRepository<Log, Long>, JpaSpecificationExecutor<Log> {
}
