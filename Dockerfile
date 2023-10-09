FROM openjdk:17-jdk

COPY target/HealthChecker-0.0.1-SNAPSHOT.jar /app/HealthChecker.jar

EXPOSE 8080

CMD ["java", "-jar", "HealthChecker.jar"]
