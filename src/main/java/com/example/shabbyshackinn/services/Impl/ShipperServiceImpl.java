package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Shippers;
import com.example.shabbyshackinn.repos.ShipperRepo;
import com.example.shabbyshackinn.services.JSONStreamProvider;
import com.example.shabbyshackinn.services.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ShipperServiceImpl implements ShipperService {
    
    @Autowired
    public ShipperServiceImpl(JSONStreamProvider jsonStreamProvider, ShipperRepo shipperRepo) {
        this.jsonStreamProvider = jsonStreamProvider;
        this.shipperRepo = shipperRepo;
    }
    
    JSONStreamProvider jsonStreamProvider;
    private final ShipperRepo shipperRepo;
    
    public ShipperServiceImpl(ShipperRepo shipperRepo) {
        this.shipperRepo = shipperRepo;
    }
    
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
        return jsonStreamProvider.getDataStream();
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
}
