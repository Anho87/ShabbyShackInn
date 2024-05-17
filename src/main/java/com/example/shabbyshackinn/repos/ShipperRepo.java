package com.example.shabbyshackinn.repos;

import com.example.shabbyshackinn.models.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipperRepo extends JpaRepository<Shippers, Long> {
    Shippers findShippersById(int externalSystemId);
}
