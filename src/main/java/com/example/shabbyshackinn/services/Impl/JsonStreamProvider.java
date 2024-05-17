package com.example.shabbyshackinn.services.Impl;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class JsonStreamProvider {

    public InputStream getDataStream() throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist");
        return url.openStream();
    }

    public URLConnection getUpdateConnection(String email) throws IOException {
        URL url = new URL("https://javabl.systementor.se/api/ShabbyShackInn/blacklist/" + email);
        return url.openConnection();
    }
}
