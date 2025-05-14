package com.example.back.repository;

import com.example.back.model.Disk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiskRepository extends JpaRepository<Disk, Long>, JpaSpecificationExecutor<Disk> {
}
