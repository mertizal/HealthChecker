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
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import javax.security.auth.login.LoginException;

@RequiredArgsConstructor
@Component
@ToString
@Slf4j
public class SendRequestService {

    public Timestamp endLifeCycleTime;
    private Map<String, Boolean> processedObjects = new HashMap<>();
    private final MonitorRepository monitorRepository;
    private final ConnectionUrlService connectionUrlService;
    @Autowired
    private final DiscordAlertBotService discordAlertBotService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private BodyControlService bodyControlService;

    @Scheduled(fixedDelay = 10000)
    public void sendRequest() {

        List<Monitor> allMonitoring = monitorRepository.FilterMonitors();

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        allMonitoring
                .forEach(monitor -> {
                            try {
                                if (!processedObjects.getOrDefault(monitor.getUrl(), false)) {
                                    Timestamp startLifeCycleTime = new Timestamp(System.currentTimeMillis());
                                    monitor.setStartLifeCycleTime(startLifeCycleTime);
                                    processedObjects.put(monitor.getUrl(), true);
                                }

                                ConnectionUrlService.Result result = connectionUrlService.httpUrlConnection(monitor.getUrl());
                                int responseStatus = result.getStatusCode();
                                Object response = result.getResponse();

                                if (monitor.getIsNecessaryBodyControl() && Objects.nonNull(response)) {

                                    String outputValue = bodyControlService.checkBody(response, monitor.getTargetPath(),
                                            monitor.getExpectedBodyValue());

                                    if (Objects.nonNull(outputValue)) {

                                        if (Objects.isNull(monitor.getDiscordToken())) {

                                            emailSenderService.sendEmail(monitor.getMail(), "Body control..",

                                                    String.format("%s adresli siteniziz body kısmında beklenen değer bu %s" +
                                                                    " iken" +
                                                                    " şu %s değer tespit edildi."
                                                            , monitor.getUrl(), monitor.getExpectedBodyValue(), outputValue));

                                        } else {

                                            emailSenderService.sendEmail(monitor.getMail(), "Body control..",

                                                    String.format("%s adresli siteniziz body kısmında beklenen değer bu %s" +
                                                                    " iken" +
                                                                    " şu %s değer tespit edildi."
                                                            , monitor.getUrl(), monitor.getExpectedBodyValue(), outputValue));

                                            discordAlertBotService.sendMessageToChannel(monitor.getDiscordToken(),
                                                    monitor.getChannelId(),
                                                    String.format("%s adresli siteniziz body kısmında beklenen değer bu %s" +
                                                                    " iken" +
                                                                    " şu %s değer tespit edildi."
                                                            , monitor.getUrl(), monitor.getExpectedBodyValue(), outputValue));
                                        }
                                    }

                                }

                                if (checkMonitorHealthIsDifferent(monitor, responseStatus)) {

                                    if (responseStatus / 100 <= 2) {

                                        monitor.setLastRunTime(now);
                                        monitor.setLastStatusCode(responseStatus);

                                        emailSenderService.sendEmail(monitor.getMail(), "Düşmez kalkmaz bir Allahtır..",

                                                String.format("%s adresli siteniz %s saniye sonra tekrar ayağa" +
                                                                " kalkmış durumda" +
                                                                " Allah bir daha düşürmesin"
                                                        , monitor.getUrl(), monitor.getFailTime()));

                                        if (Objects.nonNull(monitor.getChannelId()) && Objects.nonNull(monitor.
                                                getDiscordToken())) {

                                            discordAlertBotService.sendMessageToChannel(monitor.getDiscordToken(),
                                                    monitor.getChannelId(),
                                                    "%s adresli siteniz %s saniye sonra tekrar ayağa kalktı"
                                                            .formatted(monitor.getUrl(), monitor.getFailTime()));
                                        }

                                        monitor.setFailTime(0);
                                        log.info("A mail sending..");
                                        log.info(String.valueOf(monitor));

                                    } else {

                                        monitor.setLastRunTime(now);
                                        monitor.setLastStatusCode(responseStatus);

                                        emailSenderService.sendEmail(monitor.getMail(), "başaramadık abi..",

                                                String.format("%s adresli siteniz göçmüş olabilir.." +
                                                                " siteniz an itibariyle %s hata kodu vermektedir "
                                                        , monitor.getUrl(), monitor.getLastStatusCode()));

                                        if (Objects.nonNull(monitor.getChannelId()) && Objects.nonNull(monitor.
                                                getDiscordToken())) {

                                            discordAlertBotService.sendMessageToChannel(monitor.getDiscordToken(),
                                                    monitor.getChannelId(),
                                                    "%s adresli siteniz göçmüş olabilir.. %s durum kodu."
                                                            .formatted(monitor.getUrl(), monitor.getLastStatusCode()));
                                        }

                                        log.info("A mail sending for " + monitor.getUrl());

                                    }

                                } else {

                                    if (responseStatus / 100 <= 2) {

                                        monitor.setLastRunTime(now);
                                        monitor.setTotalStayUpTime(monitor.getInterval() + monitor.getTotalStayUpTime());

                                    } else {

                                        monitor.setFailTime(monitor.getInterval() + monitor.getFailTime());
                                        monitor.setLastRunTime(now);
                                        monitor.setTotalFailTime(monitor.getInterval() + monitor.getTotalFailTime());

                                    }
                                }

                                monitor.setTotalUpTimeRecent((int) calculateTotalUpTimeAsPercent(
                                        monitor.getTotalStayUpTime(), monitor.getTotalFailTime()));

                                monitorRepository.save(monitor);
                                log.info(monitor.getUrl() + " done.");

                            } catch (IOException | InterruptedException | LoginException | IllegalAccessException |
                                     InvocationTargetException | NoSuchMethodException e) {
                                log.error("An error occured while sending http request: " + e.getMessage());
                            }


                        }


                );

    }

    public Boolean checkMonitorHealthIsDifferent(Monitor theMonitor, int responseStatus) {

        return (theMonitor.getLastStatusCode() / 100) != (responseStatus / 100);

    }

    public double calculateTotalUpTimeAsPercent(int stayUptime, int failTime) {

        if (stayUptime == 0) {
            return 0.0;
        }

        int totalLifeTime = stayUptime + failTime;
        return (double) stayUptime / totalLifeTime * 100.0;

    }

}





