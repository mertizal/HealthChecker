package com.example.HealthChecker.api;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Dto.MonitorDto;
import com.example.HealthChecker.servies.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/monitors")
    public List<Monitor> listAllMonitors() {

        return apiService.listAllMonitors();
    }

    @PostMapping("/monitors")
    public void saveMonitor(@RequestBody MonitorDto request) {

        apiService.saveMonitor(request);
    }

    @PatchMapping("/{id}")
    public void updateMonitor(@PathVariable Long id, @RequestBody MonitorDto monitorDto) {

        apiService.partialUpdateMonitor(id, monitorDto);

    }

    @DeleteMapping("/{id}")
    public void deleteMonitor(@PathVariable Long id) {

        apiService.deleteMonitor(id);

    }

}
