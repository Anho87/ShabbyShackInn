package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Shippers;
import com.example.shabbyshackinn.repos.ShipperRepo;
import com.example.shabbyshackinn.services.JSONStreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ShippersServiceImplIntegrationTest {
    @Autowired
    ShipperRepo shipperRepo;
    
    @Autowired
    JSONStreamProvider jsonStreamProvider;
    
    ShipperServiceImpl sut;

    @Test
    void getShippersWillFetch() throws IOException {
        sut = new ShipperServiceImpl(jsonStreamProvider, shipperRepo);

        List<Shippers> shippersList = sut.getShippers();
        assertNotNull(shippersList);
        assertEquals(8, shippersList.size()); // Assuming you know the exact number of shippers

        Shippers shipper = shippersList.get(0);
        assertEquals(1, shipper.getId());
        assertEquals("Svensson-Karlsson", shipper.getCompanyName());
        assertEquals("070-569-3764", shipper.getPhone());

        Shippers shipper2 = shippersList.get(1);
        assertEquals(2, shipper2.getId());
        assertEquals("Änglund, Svensson AB", shipper2.getCompanyName());
        assertEquals("073-820-0856", shipper2.getPhone());

        Shippers shipper3 = shippersList.get(2);
        assertEquals(3, shipper3.getId());
        assertEquals("Karlsson Gruppen", shipper3.getCompanyName());
        assertEquals("076-869-7192", shipper3.getPhone());
    }

//    @Test
//    void fetchAndSaveShippersShouldSaveToDatabase() throws IOException {
//        JSONStreamProvider jsonStreamProvider = mock(JSONStreamProvider.class);
//        
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("shippers.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Shippers> shippersList = Arrays.asList(objectMapper.readValue(inputStream, Shippers[].class));
//
//        when(jsonStreamProvider.getDataStream()).thenReturn(shippersList);
//
//        sut = new ShipperServiceImpl(jsonStreamProvider, shipperRepo);
//
//        shipperRepo.deleteAll();
//
//        sut.fetchAndSaveShippers();
//
//        assertEquals(3, shipperRepo.count());
//    }
}
