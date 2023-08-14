package com.example.HealthChecker.servies;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class ConnectionUrlService {

    class Result {
        Object response;
        int statusCode;

        public Result(Object response, int statusCode) {
            this.response = response;
            this.statusCode = statusCode;
        }

        public Object getResponse() {
            return response;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    public Result httpUrlConnection(String theUrl) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(theUrl))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return new Result(response.body(), response.statusCode());

    }
}
