package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Shippers;
import com.example.shabbyshackinn.repos.ShipperRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShippersServiceTests {
    private final JsonStreamProvider jsonStreamProvider = mock(JsonStreamProvider.class);

    @Autowired
    ShipperRepo shipperRepo;
    
    ShipperServiceImpl sut;

    @BeforeEach()
    void setUp() {
        sut = new ShipperServiceImpl(jsonStreamProvider, shipperRepo);
    }

    @Test
    void whenGetShippersShouldMapCorrectly() throws IOException {
        when(jsonStreamProvider.getDataStreamShippers()).thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));

        List<Shippers> result = sut.getShippersFromAPI();

        assertEquals(3, result.size() );
        assertEquals(1, result.get(0).getId());
        assertEquals("Svensson-Karlsson", result.get(0).getCompanyName());
        assertEquals("070-569-3764", result.get(0).getPhone());

        assertEquals(2, result.get(1).getId());
        assertEquals("Änglund, Svensson AB", result.get(1).getCompanyName());
        assertEquals("073-820-0856", result.get(1).getPhone());

        assertEquals(3, result.get(2).getId());
        assertEquals("Karlsson Gruppen", result.get(2).getCompanyName());
        assertEquals("076-869-7192", result.get(2).getPhone());
        
        

    }
}
