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
    private final String apiUrl = "https://javaintegration.systementor.se/shippers";
    
    @Autowired
    public ShipperServiceImpl(JsonStreamProvider jsonStreamProvider, ShipperRepo shipperRepo) {
        this.jsonStreamProvider = jsonStreamProvider;
        this.shipperRepo = shipperRepo;
    }

    private final ShipperRepo shipperRepo;
    final JsonStreamProvider jsonStreamProvider;
    
    @Override
    public List<Shippers> getAllShippers() {
        return shipperRepo.findAll();
    }
    
    @Override
    public String addShipper(Shippers shipper) {
        shipperRepo.save(shipper);
        return "Shipper " + shipper.companyName + " is saved";
    }

    @Override
    public List<Shippers> getShippers() throws IOException {
        return jsonStreamProvider.getDataStreamShippersAsList();
    }

    @Override
    public void fetchAndSaveShippers() throws IOException {
        for(Shippers shippers : getAllShippers()){
            Shippers s = shipperRepo.findShippersById(shippers.id);
            if(s == null){
                s = (new Shippers());
            }
            s.id = shippers.id;
            s.companyName = shippers.companyName;
            s.phone = shippers.phone;
            shipperRepo.save(s);
        }
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
