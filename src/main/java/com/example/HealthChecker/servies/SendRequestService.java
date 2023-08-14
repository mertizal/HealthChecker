package com.example.HealthChecker.servies;

import com.example.HealthChecker.Models.Monitor;
import com.example.HealthChecker.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Mentions;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheEvictOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import net.dv8tion.jda.api.events.session.ReadyEvent;

import javax.security.auth.login.LoginException;

@RequiredArgsConstructor
@Component
@ToString
@Slf4j
public class SendRequestService {

    public Timestamp endLifeCycleTime;
    public Timestamp currentTimestamp;
    private Map<String, Boolean> processedObjects = new HashMap<>();

    private final MonitorRepository monitorRepository;

    private final ConnectionUrlService connectionUrlService;

    @Autowired
    private final DiscordAlertBotService discordAlertBotService;

    @Autowired
    private EmailSenderService emailSenderService;


    @Scheduled(fixedDelay = 10000)
    public void sendRequest() throws IOException {

        List<Monitor> allMonitoring = monitorRepository.findAll();

        allMonitoring
                .forEach(monitor -> {

                            currentTimestamp = new Timestamp(System.currentTimeMillis());
                            int remainingSeconds = (int) ((currentTimestamp.getTime() - monitor.getLastRunTime().getTime()) / 1000);

                            if (remainingSeconds >= monitor.getInterval()) {
                                try {

                                    if (!processedObjects.getOrDefault(monitor.getUrl(), false)) {

                                        Timestamp startLifeCycleTime = new Timestamp(System.currentTimeMillis());
                                        monitor.setStartLifeCycleTime(startLifeCycleTime);
                                        processedObjects.put(monitor.getUrl(), true);
                                    }

                                    int responseStatus = connectionUrlService.httpUrlConnection(monitor.getUrl()).getStatusCode();
                                    if (checkMonitorHealthIsDifferent(monitor, responseStatus)) {

                                        if (responseStatus / 100 <= 2) {

                                            monitor.setLastRunTime(currentTimestamp);
                                            monitor.setLastStatusCode(responseStatus);

                                            emailSenderService.sendEmail(monitor.getMail(), "Düşmez kalkmaz bir Allahtır..",

                                                    String.format("%s adresli siteniz %s saniye sonra tekrar ayağa" +
                                                                    " kalkmış durumda" +
                                                                    " Allah bir daha düşürmesin"
                                                            , monitor.getUrl(), monitor.getFailTime(),
                                                            monitor.getLastStatusCode()));
                                            monitor.setFailTime(0);

                                            log.info("A mail sending..");
                                            log.info(String.valueOf(monitor));
                                        } else {


                                            monitor.setLastRunTime(currentTimestamp);
                                            monitor.setLastStatusCode(responseStatus);
                                            emailSenderService.sendEmail(monitor.getMail(), "başaramadık abi..",

                                                    String.format("%s adresli siteniz göçmüş olabilir.." +
                                                                    " siteniz an itibariyle %s hata kodu vermektedir "
                                                            , monitor.getUrl(), monitor.getLastStatusCode()));

                                            log.info("A mail sending for " + monitor.getUrl());


                                        }

                                    } else {

                                        if (responseStatus / 100 <= 2) {

                                            monitor.setLastRunTime(currentTimestamp);
                                            monitor.setTotalStayUpTime(monitor.getInterval() + monitor.getTotalStayUpTime());


                                        } else {

                                            monitor.setFailTime(monitor.getInterval() + monitor.getFailTime());
                                            monitor.setLastRunTime(currentTimestamp);
                                            monitor.setTotalFailTime(monitor.getInterval() + monitor.getTotalFailTime());

                                        }
                                    }

                                    monitor.setTotalUpTimeRecent((int) calculateTotalUpTimeAsPercent(
                                            monitor.getTotalStayUpTime(), monitor.getTotalFailTime()));
                                    monitorRepository.save(monitor);
                                    log.info(monitor.getUrl() + " done.");

                                } catch (IOException | InterruptedException e) {
                                    log.error("An error occured while sending http request: " + e.getMessage());
                                }

                            }

                        }

                );

    }



    public Boolean checkMonitorHealthIsDifferent(Monitor theMonitor, int responseStatus) {

        if ((theMonitor.getLastStatusCode() / 100) != (responseStatus / 100)) {

            return true;
        }

        return false;

    }

    public double calculateTotalUpTimeAsPercent(int stayUptime, int failTime) {

        if (stayUptime == 0) {

            return 0.0;
        }

        int totalLifeTime = stayUptime + failTime;
        return (double) stayUptime / totalLifeTime * 100.0;

    }

}





