package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Request.TestRequest;
import com.example.HealthChecker.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final MonitorRepository monitorRepository;

    public List<Monitor> listAllMonitors() {
        return monitorRepository.findAll();
    }

    public void saveMonitor(TestRequest testRequest) {
        if (Objects.nonNull(testRequest)) {
            Monitor themonitor = new Monitor();
            themonitor.setInterval(testRequest.getInterval());
            themonitor.setUrl(testRequest.getUrl());
            themonitor.setMail(testRequest.getMail());
            monitorRepository.save(themonitor);
        }
    }
}
