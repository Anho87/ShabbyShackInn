package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Shippers;
import com.example.shabbyshackinn.repos.ShipperRepo;
import com.example.shabbyshackinn.services.ShipperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperServiceImpl implements ShipperService {
    
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
}
