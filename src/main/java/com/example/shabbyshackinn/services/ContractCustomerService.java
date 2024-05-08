package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {
    
    List<ContractCustomer> getAllContractCustomers();
    String addContractCustomer(ContractCustomer contractCustomer);
    
    
    
    
    
    
    
    
    
    
    
    
    
    String saveOrUpdateContractCustomer(ContractCustomer contractCustomer);
    ContractCustomer getContractCustomerByExternalSystemId(int externalSystemId);
    String updateContractCustomer(ContractCustomer existingContractCustomer, ContractCustomer contractCustomer);
    DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer contractCustomer);
    ContractCustomer detailedContractCustomerToContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto);
    DetailedContractCustomerDto findDetailedContractCustomerById(Long id);
}
