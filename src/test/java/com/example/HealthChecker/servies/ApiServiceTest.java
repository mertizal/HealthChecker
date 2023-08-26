package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.Dto.MonitorDto;
import com.example.HealthChecker.repository.MonitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ApiServiceTest {

    @InjectMocks
    private ApiService apiService;

    @Mock
    private MonitorRepository monitorRepository;

    @Test
    void saveMonitor() {
        // given
        MonitorDto request = new MonitorDto();
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
        MonitorDto request = null;

        // when
        apiService.saveMonitor(request);

        // then
        Mockito.verify(monitorRepository, Mockito.times(0)).save(Mockito.any(Monitor.class));
    }
}
