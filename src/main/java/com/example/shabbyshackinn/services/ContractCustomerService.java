package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;

public interface ContractCustomerService {

    List<ContractCustomer> getAllContractCustomers();

    String addContractCustomer(ContractCustomer contractCustomer);

    List<MiniContractCustomerDto> getAllMiniContractCustomers();

    MiniContractCustomerDto contractCustomerToMiniContractCustomerDto(ContractCustomer contractCustomer);

    String saveOrUpdateContractCustomer(ContractCustomer contractCustomer);

    ContractCustomer getContractCustomerByExternalSystemId(int externalSystemId);

    String updateContractCustomer(ContractCustomer existingContractCustomer, ContractCustomer contractCustomer);

    DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer contractCustomer);

    DetailedContractCustomerDto findDetailedContractCustomerById(Long id);

    List<ContractCustomer> findAllBySearchAndSortOrder(String searchWord, Sort sort);

    void fetchAndSaveContractCustomers() throws IOException;

    List<ContractCustomer> getContractCustomers() throws IOException;
}
