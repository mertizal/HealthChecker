FROM openjdk:17-jdk

COPY target/HealthChecker-1.0.0.jar /app/HealthChecker.jar

EXPOSE 8080

CMD ["java", "-jar", "HealthChecker.jar"]
