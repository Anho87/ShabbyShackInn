package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Shippers;
import com.example.shabbyshackinn.repos.ShipperRepo;
import com.example.shabbyshackinn.services.ShipperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperServiceImpl implements ShipperService {

    private final ObjectMapper mapper = new JsonMapper();
    private final String apiUrl = "https://javabl.systementor.se/api/ShabbyShackInn/blacklist";

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
}
