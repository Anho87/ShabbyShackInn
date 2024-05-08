package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.repos.ContractCustomerRepo;
import com.example.shabbyshackinn.services.ContractCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {
    
    private final ContractCustomerRepo contractCustomerRepo;


    public ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo) {
        this.contractCustomerRepo = contractCustomerRepo;
    }


    @Override
    public List<ContractCustomer> getAllContractCustomers() {
        return contractCustomerRepo.findAll();
    }

    @Override
    public String addContractCustomer(ContractCustomer contractCustomer) {
        contractCustomerRepo.save(contractCustomer);
        return "Contract customer" + contractCustomer.companyName + "is saved!";
    }
}
