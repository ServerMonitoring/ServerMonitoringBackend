package com.example.back.repository;

import com.example.back.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServerRepository extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {
}
