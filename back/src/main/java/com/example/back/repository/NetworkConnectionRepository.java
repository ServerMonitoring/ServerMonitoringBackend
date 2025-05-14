package com.example.back.repository;

import com.example.back.model.NetworkConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NetworkConnectionRepository extends JpaRepository<NetworkConnection, Long>, JpaSpecificationExecutor<NetworkConnection> {
}
