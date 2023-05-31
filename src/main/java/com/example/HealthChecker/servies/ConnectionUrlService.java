package com.example.HealthChecker.servies;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class ConnectionUrlService {

    public int httpUrlConnection(String theUrl) throws IOException {

        URL url = new URL(theUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        int status = con.getResponseCode();
        con.disconnect();
        return status;
    }
}
