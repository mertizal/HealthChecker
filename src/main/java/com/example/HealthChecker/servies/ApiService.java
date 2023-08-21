package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Dto.MonitorDto;
import com.example.HealthChecker.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final MonitorRepository monitorRepository;

    public List<Monitor> listAllMonitors() {
        return monitorRepository.findAll();
    }

    public void saveMonitor(MonitorDto request) {
        if (Objects.nonNull(request)) {
            Monitor themonitor = new Monitor();
            themonitor.setInterval(request.getInterval());
            themonitor.setUrl(request.getUrl());
            themonitor.setMail(request.getMail());
            monitorRepository.save(themonitor);
        }
    }

    public void partialUpdateMonitor(Long id, MonitorDto monitorDto) {

        Optional<Monitor> optionalMonitor = monitorRepository.findById(id);

        if (optionalMonitor.isPresent()) {

            Monitor existingMonitor = optionalMonitor.get();

            if (monitorDto.getUrl() != null) {

                existingMonitor.setUrl(monitorDto.getUrl());
            }
            if (monitorDto.getMail() != null) {

                existingMonitor.setMail(monitorDto.getMail());
            }
            if (monitorDto.getInterval() > 0) {

                existingMonitor.setInterval(monitorDto.getInterval());
            }
            if (monitorDto.getChannelId() != null) {

                existingMonitor.setChannelId(monitorDto.getChannelId());
            }


        }

    }

    public void deleteMonitor(Long id) {

        if (monitorRepository.findById(id).isPresent()) {

            monitorRepository.deleteById(id);
        }

    }
}
