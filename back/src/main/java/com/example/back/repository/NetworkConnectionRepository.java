package com.example.back.repository;

import com.example.back.model.NetworkConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkConnectionRepository extends JpaRepository<NetworkConnection, Integer> {
}
