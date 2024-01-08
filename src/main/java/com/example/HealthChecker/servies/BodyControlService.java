package com.example.HealthChecker.servies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationTargetException;

@Component
public class BodyControlService {

    public String checkBody(Object response, String targetPath, String expectedBody) throws
            InvocationTargetException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {

        String dataString = JsonPath.read(response.toString(), targetPath);

        if(!dataString.equalsIgnoreCase(expectedBody)){

            return dataString;
        }
        return null;
    }

}
