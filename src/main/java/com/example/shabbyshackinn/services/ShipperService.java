package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.Shippers;

import java.util.List;

public interface ShipperService {
    List<Shippers> getAllShippers();
    String addShipper(Shippers shippers);
}
