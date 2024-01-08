package com.example.HealthChecker.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    @Nullable
    private String targetPath;

    @Nullable
    private String expectedBodyValue;
    private String mail;
    private int interval;

    @Nullable
    private String channelId;

    @JsonIgnore
    @Nullable
    private String discordToken;

    // @Nullable
    // private String actualBodyValue;

    @Column(columnDefinition = "boolean default false")
    private Boolean isNecessaryBodyControl;

    @JsonIgnore
    @Nullable
    private int lastStatusCode;

    @JsonIgnore
    @Nullable
    private Timestamp lastRunTime;

    @JsonIgnore
    @Nullable
    private Timestamp startLifeCycleTime;

    @JsonIgnore
    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int totalStayUpTime;

    @JsonIgnore
    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int totalFailTime;

    @JsonIgnore
    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int totalUpTimeRecent;

    @JsonIgnore
    @Nullable
    @Column(columnDefinition = "integer default 0")
    private int failTime;

}
