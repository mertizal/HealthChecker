package com.example.HealthChecker.Request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TestRequest {
    private String url;
    private String mail;
    private int interval;
}
