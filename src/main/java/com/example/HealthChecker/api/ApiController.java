package com.example.HealthChecker.api;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Request.Request;
import com.example.HealthChecker.servies.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void saveMonitor(@RequestBody Request request) {

        apiService.saveMonitor(request);
    }


}
