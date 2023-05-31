package com.example.HealthChecker.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "monitor")
@Getter
@Setter
@ToString
public class Monitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String mail;
    private int interval;

    private int lastStatusCode;
    private Timestamp lastRunTime;
}
