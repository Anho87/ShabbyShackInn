package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.Shippers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class JSONStreamProvider {
    public List<Shippers> getDataStream() throws IOException {
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        try (InputStream inputStream = url.openStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(inputStream, Shippers[].class));
        }
    }
}
