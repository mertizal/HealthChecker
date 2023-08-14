package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Request.Request;
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

    public void saveMonitor(Request request) {
        if (Objects.nonNull(request)) {
            Monitor themonitor = new Monitor();
            themonitor.setInterval(request.getInterval());
            themonitor.setUrl(request.getUrl());
            themonitor.setMail(request.getMail());
            monitorRepository.save(themonitor);
        }
    }
}
