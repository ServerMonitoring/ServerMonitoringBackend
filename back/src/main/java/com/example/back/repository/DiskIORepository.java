package com.example.back.repository;

import com.example.back.model.DiskIO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiskIORepository extends JpaRepository<DiskIO, Long>, JpaSpecificationExecutor<DiskIO> {
}
