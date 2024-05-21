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
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist/" + email);
        return url.openConnection();
    }

    public InputStream getDataStreamBlacklistCheck(String email) throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklistcheck/" + email);
        return url.openStream();
    }
}
