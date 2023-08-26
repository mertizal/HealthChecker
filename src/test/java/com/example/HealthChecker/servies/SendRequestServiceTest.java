package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.repository.MonitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(Parameterized.class)
class SendRequestServiceTest {

    @InjectMocks
    private SendRequestService sendRequestService;

    @Mock
    private MonitorRepository monitorRepository;
    @Mock
    private ConnectionUrlService connectionUrlService;
    @Mock
    private EmailSenderService emailSenderService;


    @ParameterizedTest
    @CsvSource({
            "100, 100, 50.0",
            "200, 100, 66.66666666666666",
            "0, 100, 0.0"
    })

    public void testCalculateTotalUpTimeAsPercent(int totalStayUpTime, int totalFailTime, double expectedPercentage) {
        double result = sendRequestService.calculateTotalUpTimeAsPercent(totalStayUpTime, totalFailTime);
        assertEquals(expectedPercentage, result, 0.001);

    }


    @Test
    public void testCheckMonitorHealthIsDifferent() {

        Monitor testMonitor = new Monitor();
        testMonitor.setLastStatusCode(200);
        int testResponseStatus = 404;
        boolean result = sendRequestService.checkMonitorHealthIsDifferent(testMonitor,testResponseStatus);
        assertEquals(true,result);




    }

}