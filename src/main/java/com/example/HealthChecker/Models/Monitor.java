package com.example.HealthChecker.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.lang.Nullable;

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

    @Nullable
    private String channelId;

    @Nullable
    private int lastStatusCode;
    private Timestamp lastRunTime;

    private Timestamp startLifeCycleTime;

    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int totalStayUpTime;

    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int totalFailTime;

    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int totalUpTimeRecent;

    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int failTime;

}
