package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.repos.ShipperRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ShipperServiceImplIntegrationTest {
    @Autowired
    ShipperRepo shipperRepo;

    @Autowired
    JsonStreamProvider jsonStreamProvider;

    ShipperServiceImpl sut;

    @Test
    void getShippersWillFetch() throws IOException {
        sut = new ShipperServiceImpl(jsonStreamProvider, shipperRepo);
        Scanner s = new Scanner(sut.jsonStreamProvider.getDataStreamShippers()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"id\""));
        assertTrue(result.contains("\"email\""));
        assertTrue(result.contains("\"companyName\""));
        assertTrue(result.contains("\"contactName\""));
        assertTrue(result.contains("\"contactTitle\""));
        assertTrue(result.contains("\"streetAddress\""));
        assertTrue(result.contains("\"city\""));
        assertTrue(result.contains("\"postalCode\""));
        assertTrue(result.contains("\"country\""));
        assertTrue(result.contains("\"phone\""));
        assertTrue(result.contains("\"fax\""));
        
    }

//        List<Shippers> shippersList = sut.getShippers();
//        assertNotNull(shippersList);
//        assertEquals(8, shippersList.size());
//
//        Shippers shipper = shippersList.get(0);
//        assertEquals(1, shipper.getId());
//        assertEquals("Svensson-Karlsson", shipper.getCompanyName());
//        assertEquals("070-569-3764", shipper.getPhone());
//
//        Shippers shipper2 = shippersList.get(1);
//        assertEquals(2, shipper2.getId());
//        assertEquals("Änglund, Svensson AB", shipper2.getCompanyName());
//        assertEquals("073-820-0856", shipper2.getPhone());
//
//        Shippers shipper3 = shippersList.get(2);
//        assertEquals(3, shipper3.getId());
//        assertEquals("Karlsson Gruppen", shipper3.getCompanyName());
//        assertEquals("076-869-7192", shipper3.getPhone());
//
//        Shippers shipper4 = shippersList.get(3);
//        assertEquals(4, shipper3.getId());
//        assertEquals("Nilsson Kommanditbolag", shipper4.getCompanyName());
//        assertEquals("070-661-8894", shipper4.getPhone());
//
//        Shippers shipper5 = shippersList.get(4);
//        assertEquals(5, shipper3.getId());
//        assertEquals("Åslund-Gustafsson", shipper5.getCompanyName());
//        assertEquals("076-552-6784", shipper5.getPhone());
//
//        Shippers shipper6 = shippersList.get(5);
//        assertEquals(6, shipper3.getId());
//        assertEquals("Östlund, Änglund Group", shipper6.getCompanyName());
//        assertEquals("070-992-7785", shipper6.getPhone());
//
//        Shippers shipper7 = shippersList.get(6);
//        assertEquals(7, shipper3.getId());
//        assertEquals("Åslund Kommanditbolag", shipper7.getCompanyName());
//        assertEquals("070-656-1853", shipper7.getPhone());
//
//        Shippers shipper8 = shippersList.get(7);
//        assertEquals(8, shipper3.getId());
//        assertEquals("Johansson-Änglund", shipper8.getCompanyName());
//        assertEquals("070-136-6555", shipper8.getPhone());
    
}
