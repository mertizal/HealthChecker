package com.example.HealthChecker.repository;

import com.example.HealthChecker.Models.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<Monitor,Long> {

}
