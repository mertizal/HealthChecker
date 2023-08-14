package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Request.Request;
import com.example.HealthChecker.repository.MonitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @InjectMocks
    private ApiService apiService;

    @Mock
    private MonitorRepository monitorRepository;

    @Test
    void saveMonitor() {
        // given
        Request request = new Request();
        request.setInterval(1000);
        request.setMail("mert@hotmail.com");
        request.setUrl("https://google.com");

        // when
        apiService.saveMonitor(request);

        // then
        Mockito.verify(monitorRepository, Mockito.times(1)).save(Mockito.any(Monitor.class));
    }

    @Test
    void saveMonitor_when_TestRequest_is_null() {
        // given
        Request request = null;

        // when
        apiService.saveMonitor(request);

        // then
        Mockito.verify(monitorRepository, Mockito.times(0)).save(Mockito.any(Monitor.class));
    }

}