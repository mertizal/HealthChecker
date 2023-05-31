package com.example.HealthChecker;

import com.example.HealthChecker.servies.SendRequestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class HealthCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCheckerApplication.class, args);
	}
}
