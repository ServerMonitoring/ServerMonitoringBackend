package com.example.back.repository;

import com.example.back.model.NetInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NetInterfaceRepository extends JpaRepository<NetInterface, Long>, JpaSpecificationExecutor<NetInterface> {
}
