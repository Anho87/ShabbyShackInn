package com.example.shabbyshackinn.services.Impl;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class JsonStreamProvider {

    public InputStream getDataStreamFullBlacklist() throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist");
        return url.openStream();
    }
    public URLConnection getAddToBlacklistConnection() throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist");
        return url.openConnection();
    }

    public URLConnection getUpdateBlacklistConnection(String email) throws IOException {
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

    public InputStream getDataStreamBlacklistCheck(String email) throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklistcheck/" + email);
        return url.openStream();
    }
}
