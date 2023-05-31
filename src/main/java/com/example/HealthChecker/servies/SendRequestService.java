package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Component
@ToString
@Slf4j
public class SendRequestService {

    private final MonitorRepository monitorRepository;

    private final ConnectionUrlService connectionUrlService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Scheduled(fixedDelay = 10000)
    public void sendRequestTest() throws IOException {

        List<Monitor> allMonitoring = monitorRepository.findAll();

        allMonitoring
                .forEach(monitor -> {
                            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                            long remainingSeconds = (currentTimestamp.getTime() - monitor.getLastRunTime().getTime()) / 1000;
                            if (remainingSeconds >= monitor.getInterval()) {
                                try {
                                    int responseStatus = connectionUrlService.httpUrlConnection(monitor.getUrl());
                                    if (responseStatus == 200) {
                                        monitor.setLastRunTime(currentTimestamp);
                                        monitor.setLastStatusCode(responseStatus);
                                        monitorRepository.save(monitor);
                                        log.info(String.valueOf(monitor));
                                    } else {
                                        monitor.setLastRunTime(currentTimestamp);
                                        monitor.setLastStatusCode(responseStatus);
                                        monitorRepository.save(monitor);
                                        emailSenderService.sendEmail(monitor.getMail(), "başaramadık abi..",

                                                String.format("%s adresli siteniz göçmüş olabilir.." +
                                                                " siteniz an itibariyle %s hata kodu vermektedir "
                                                        , monitor.getUrl(), monitor.getLastStatusCode()));

                                        log.info("A mail sending..");
                                    }

                                } catch (IOException e) {
                                    log.error("An error occured while sending http request: " + e.getMessage());
                                }

                            }

                        }
                );

    }

}
