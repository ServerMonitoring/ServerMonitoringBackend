package com.example.back.repository;

import com.example.back.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public interface ServerRepository extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Server s SET s.online = false " +
            "WHERE s.online = true " +
            "AND (s.lastSeenAt IS NULL OR s.lastSeenAt < :cutoff)")
    int markOfflineIfLastSeenBefore(@Param("cutoff") Instant cutoff);
}
