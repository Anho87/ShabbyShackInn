package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.Shippers;

import java.io.IOException;
import java.util.List;

public interface ShipperService {
    

    List<Shippers> getShippers() throws IOException;
    

    List<Shippers> getShippersFromAPI();
}
