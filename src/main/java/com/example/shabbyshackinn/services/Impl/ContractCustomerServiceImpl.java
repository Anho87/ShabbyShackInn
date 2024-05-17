package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.repos.ContractCustomerRepo;
import com.example.shabbyshackinn.services.ContractCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    
    @Override
    public List<MiniContractCustomerDto> getAllMiniContractCustomers(){
        return contractCustomerRepo.findAll().stream().map(c -> contractCustomerToMiniContractCustomerDto(c)).toList();
    }
    
    
    @Override
    public MiniContractCustomerDto contractCustomerToMiniContractCustomerDto(ContractCustomer contractCustomer){
        return MiniContractCustomerDto.builder().id(contractCustomer.id).companyName(contractCustomer.companyName)
                .contactName(contractCustomer.contactName).country(contractCustomer.country).build();
    }
    
    @Override
    public String saveOrUpdateContractCustomer(ContractCustomer contractCustomer) {
        ContractCustomer existingContractCustomer = getContractCustomerByExternalSystemId(contractCustomer.externalSystemId);
        if(existingContractCustomer != null){
           return updateContractCustomer(existingContractCustomer, contractCustomer);
        }else {
            return addContractCustomer(contractCustomer);
        }
    }

    @Override
    public ContractCustomer getContractCustomerByExternalSystemId(int externalSystemId) {
        return contractCustomerRepo.findContractCustomerByExternalSystemId(externalSystemId);
    }

    @Override
    public String updateContractCustomer(ContractCustomer existingContractCustomer, ContractCustomer contractCustomer) {
        existingContractCustomer.companyName = contractCustomer.companyName;
        existingContractCustomer.contactName = contractCustomer.contactName;
        existingContractCustomer.contactTitle = contractCustomer.contactTitle;
        existingContractCustomer.streetAddress = contractCustomer.streetAddress;
        existingContractCustomer.city = contractCustomer.city;
        existingContractCustomer.postalCode = contractCustomer.postalCode;
        existingContractCustomer.country = contractCustomer.country;
        existingContractCustomer.phone = contractCustomer.phone;
        existingContractCustomer.fax = contractCustomer.fax;

        contractCustomerRepo.save(existingContractCustomer);
        return "Contract customer " + existingContractCustomer.companyName + " is updated";
    }

    @Override
    public DetailedContractCustomerDto contractCustomerToDetailedContractCustomerDto(ContractCustomer contractCustomer) {
        return DetailedContractCustomerDto.builder()
                .id(contractCustomer.id)
                .externalSystemId(contractCustomer.externalSystemId)
                .companyName(contractCustomer.companyName)
                .contactName(contractCustomer.contactName)
                .contactTitle(contractCustomer.contactTitle)
                .streetAddress(contractCustomer.streetAddress)
                .city(contractCustomer.city)
                .postalCode(contractCustomer.postalCode)
                .country(contractCustomer.country)
                .phone(contractCustomer.phone)
                .fax(contractCustomer.fax)
                .build();
                
    }

    @Override
    public ContractCustomer detailedContractCustomerToContractCustomer(DetailedContractCustomerDto detailedContractCustomerDto) {
        return ContractCustomer.builder().id(detailedContractCustomerDto.getId())
                .externalSystemId(detailedContractCustomerDto.getExternalSystemId())
                .companyName(detailedContractCustomerDto.getCompanyName())
                .contactName(detailedContractCustomerDto.getContactName())
                .contactTitle(detailedContractCustomerDto.getContactTitle())
                .streetAddress(detailedContractCustomerDto.getStreetAddress())
                .city(detailedContractCustomerDto.getCity())
                .postalCode(detailedContractCustomerDto.getPostalCode())
                .country(detailedContractCustomerDto.getCountry())
                .phone(detailedContractCustomerDto.getPhone())
                .fax(detailedContractCustomerDto.getFax())
                .build();
    }

    @Override
    public DetailedContractCustomerDto findDetailedContractCustomerById(Long id){
        return contractCustomerToDetailedContractCustomerDto(contractCustomerRepo.findById(id).get());
    }

    @Override
    public List<ContractCustomer> findAllBySearchAndSortOrder(String searchWord, Sort sort) {
        return contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains
                (searchWord,searchWord,searchWord,sort);
    }
}
