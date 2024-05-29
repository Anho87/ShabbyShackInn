package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.repos.ContractCustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ContractCustomerRepoIntegrationTest {
    @Autowired
    private ContractCustomerRepo contractCustomerRepo;

    @BeforeEach
    void setUp() {
        ContractCustomer contractCustomer1 =
                new ContractCustomer(1L, 1, "Persson Kommanditbolag"
                        , "Maria Åslund", "gardener", "Anderssons Gata 259"
                        , "Kramland", 60843, "Sverige", "076-340-7143"
                        , "1500-16026");
        ContractCustomer contractCustomer2 =
                new ContractCustomer(2L, 2, "Karlsson-Eriksson"
                        , "Jörgen Gustafsson", "philosopher", "Undre Villagatan 451"
                        , "Alingtorp", 28838, "Sverige", "070-369-5518"
                        , "7805-209976");
        ContractCustomer contractCustomer3 =
                new ContractCustomer(3L, 3, "Eriksson Group"
                        , "Anna Karlsson", "journalist", "Johanssons Väg 036"
                        , "Arlöv", 77616, "Norge", "076-904-2433"
                        , "8653-585976");

        contractCustomerRepo.save(contractCustomer1);
        contractCustomerRepo.save(contractCustomer2);
        contractCustomerRepo.save(contractCustomer3);
    }

    @Test
    void findContractCustomerByExternalSystemIdTest() {
        ContractCustomer contractCustomer = contractCustomerRepo.findContractCustomerByExternalSystemId(1);
        assertEquals(1L, contractCustomer.id);
        assertEquals(1, contractCustomer.externalSystemId);
        assertEquals("Persson Kommanditbolag", contractCustomer.companyName);
        assertEquals("Maria Åslund", contractCustomer.contactName);
        assertEquals("gardener", contractCustomer.contactTitle);
        assertEquals("Anderssons Gata 259", contractCustomer.streetAddress);
        assertEquals("Kramland", contractCustomer.city);
        assertEquals(60843, contractCustomer.postalCode);
        assertEquals("Sverige", contractCustomer.country);
        assertEquals("076-340-7143", contractCustomer.phone);
        assertEquals("1500-16026", contractCustomer.fax);
    }
}
