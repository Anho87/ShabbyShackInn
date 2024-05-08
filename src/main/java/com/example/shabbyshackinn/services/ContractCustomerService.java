package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {
    
    List<ContractCustomer> getAllContractCustomers();
    String addContractCustomer(ContractCustomer contractCustomer);
}
