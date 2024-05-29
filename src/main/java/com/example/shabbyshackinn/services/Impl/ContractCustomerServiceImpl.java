package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.models.AllContractCustomers;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.repos.ContractCustomerRepo;
import com.example.shabbyshackinn.services.ContractCustomerService;
import com.example.shabbyshackinn.services.XmlStreamProvider;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.catalog.Catalog;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ContractCustomerServiceImpl implements ContractCustomerService {
    
    @Autowired
    public ContractCustomerServiceImpl(XmlStreamProvider xmlStreamProvider, ContractCustomerRepo contractCustomerRepo) {
        this.xmlStreamProvider = xmlStreamProvider;
        this.contractCustomerRepo = contractCustomerRepo;
    }
    
    public ContractCustomerServiceImpl(ContractCustomerRepo contractCustomerRepo) {
        this.contractCustomerRepo = contractCustomerRepo;
    }
    
    XmlStreamProvider xmlStreamProvider;
    final ContractCustomerRepo contractCustomerRepo;
    
    
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
    public DetailedContractCustomerDto findDetailedContractCustomerById(Long id){
        return contractCustomerToDetailedContractCustomerDto(contractCustomerRepo.findById(id).get());
    }

    @Override
    public List<ContractCustomer> findAllBySearchAndSortOrder(String searchWord, Sort sort) {
        return contractCustomerRepo.findAllByCompanyNameContainsOrContactNameContainsOrCountryContains
                (searchWord,searchWord,searchWord,sort);
    }

    @Override
    public List<ContractCustomer> getContractCustomers() throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);
        InputStream stream =  xmlStreamProvider.getDataStream();
        AllContractCustomers allContractCustomers = xmlMapper.readValue(stream,
                AllContractCustomers.class
        );

        return allContractCustomers.contractCustomers;
    }
    @Override
    public void fetchAndSaveContractCustomers() throws IOException {
        for(ContractCustomer contractCustomer : getContractCustomers()){
            ContractCustomer c = contractCustomerRepo.findContractCustomerByExternalSystemId(contractCustomer.externalSystemId);
            if(c == null){
                c = (new ContractCustomer());
            }
//            c.externalSystemId= contractCustomer.externalSystemId;
//            c.companyName = contractCustomer.companyName;
//            c.contactName= contractCustomer.contactName;
//            c.contactTitle= contractCustomer.contactTitle;
//            c.streetAddress= contractCustomer.streetAddress;
//            c.city= contractCustomer.city;
//            c.postalCode= contractCustomer.postalCode;
//            c.country= contractCustomer.country;
//            c.phone= contractCustomer.phone;
//            c.fax= contractCustomer.fax;
            contractCustomerRepo.save(c);
        }
    }
}
