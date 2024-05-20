package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.Shippers;

import java.io.IOException;
import java.util.List;

public interface ShipperService {
    List<Shippers> getAllShippers();
    String addShipper(Shippers shippers);

    List<Shippers> getShippers() throws IOException;

    void fetchAndSaveShippers() throws IOException;
    

    List<Shippers> getShippersFromAPI();
}
