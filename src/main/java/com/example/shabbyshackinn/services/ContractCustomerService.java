package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {
    
    List<ContractCustomer> getAllContractCustomers();
    String addContractCustomer(ContractCustomer contractCustomer);
    List<MiniContractCustomerDto> getAllMiniContractCustomers();
    MiniContractCustomerDto contractCustomerToMiniContractCustomerDto(ContractCustomer contractCustomer);
}
