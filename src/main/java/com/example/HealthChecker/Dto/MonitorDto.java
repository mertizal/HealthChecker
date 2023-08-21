package com.example.HealthChecker.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorDto {

    private String url;
    private String mail;
    private int interval;
    private String channelId;
}
