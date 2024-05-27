package com.example.shabbyshackinn.services;


import com.example.shabbyshackinn.configuration.IntegrationProperties;
import com.example.shabbyshackinn.models.Shippers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

@Service
public class JsonStreamProvider {
    
    @Autowired
    private IntegrationProperties properties;   

    public InputStream getDataStreamFullBlacklist() throws IOException {
        URL url = new URL(properties.getBlackListProperties().getUrl());
        return url.openStream();
    }

    public URLConnection getAddToBlacklistConnection() throws IOException {
        URL url = new URL(properties.getBlackListProperties().getUrl());
        return url.openConnection();
    }

    public InputStream getDataStreamShippers() throws IOException {
        URL url = new URL(properties.getShippersProperties().getUrl());
        return url.openStream();
    }


    public List<Shippers> getDataStreamShippersAsList() throws IOException {
        URL url = new URL(properties.getShippersProperties().getUrl());
        try (InputStream inputStream = url.openStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return Arrays.asList(objectMapper.readValue(inputStream, Shippers[].class));
        }
    }

    public URLConnection getUpdateBlacklistConnection(String email) throws IOException {
        URL url = new URL(   properties.getBlackListProperties().getUrl() + "/" + email);
        return url.openConnection();
    }

    public InputStream getDataStreamBlacklistCheck(String email) throws IOException {
        URL url = new URL(properties.getBlackListProperties().getUrl() + "check/" + email);
        return url.openStream();
    }

}
