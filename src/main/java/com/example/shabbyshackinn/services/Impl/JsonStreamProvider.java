package com.example.shabbyshackinn.services.Impl;


import com.example.shabbyshackinn.models.Shippers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonStreamProvider {

    public InputStream getDataStream() throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist");
        return url.openStream();
    }

    public InputStream getDataStreamShippers() throws IOException {
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        return url.openStream();
    }

    public List<Shippers> getDataStreamShippersAsList() throws IOException {
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        try (InputStream inputStream = url.openStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(inputStream, Shippers[].class));
        }
    }

    public URLConnection getUpdateConnection(String email) throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist/" + email);
        return url.openConnection();
    }
}
