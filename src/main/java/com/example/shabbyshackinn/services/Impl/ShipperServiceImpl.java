package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Shippers;
import com.example.shabbyshackinn.repos.ShipperRepo;
import com.example.shabbyshackinn.services.JsonStreamProvider;
import com.example.shabbyshackinn.services.ShipperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipperServiceImpl implements ShipperService {

    private final ObjectMapper mapper = new JsonMapper();
    
    @Autowired
    public ShipperServiceImpl(JsonStreamProvider jsonStreamProvider, ShipperRepo shipperRepo) {
        this.jsonStreamProvider = jsonStreamProvider;
        this.shipperRepo = shipperRepo;
    }

    final ShipperRepo shipperRepo;
    final JsonStreamProvider jsonStreamProvider;

    @Override
    public List<Shippers> getShippers() throws IOException {
        return jsonStreamProvider.getDataStreamShippersAsList();
    }
    

    @Override
    public List<Shippers> getShippersFromAPI() {
        try {
            InputStream stream = jsonStreamProvider.getDataStreamShippers();
            return mapper.readValue(stream, mapper.getTypeFactory().constructCollectionType(List.class, Shippers.class));
        } catch (IOException e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }
}
