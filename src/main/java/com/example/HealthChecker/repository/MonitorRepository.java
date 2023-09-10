package com.example.HealthChecker.repository;

import com.example.HealthChecker.Models.Monitor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonitorRepository extends JpaRepository<Monitor,Long> {

    @Query(value = "SELECT * FROM monitor WHERE last_run_time is null or last_run_time + \"interval\" * interval '1 second' <= now() + interval '3 hour'", nativeQuery = true)
    List<Monitor> FiterBlaBla();

}
