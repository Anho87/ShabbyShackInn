package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.configuration.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class XmlStreamProvider {

    @Autowired
    private IntegrationProperties properties;

    public InputStream getDataStream() throws IOException {
        URL url = new URL(properties.getContractCustomerProperties().getUrl());
        return url.openStream();
    }
}
