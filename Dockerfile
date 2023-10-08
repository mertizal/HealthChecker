# Temel Docker imajını belirle
FROM openjdk:17-jdk

# Uygulama dosyalarını imaja kopyala
COPY target/HealthCheckher-1.0.0.jar /app/HealthChecker.jar

EXPOSE 8080

# Uygulama çalıştırma komutunu tanımla
CMD ["java", "-jar", "HealthChecker.jar"]