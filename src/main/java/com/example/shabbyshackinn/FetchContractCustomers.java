package com.example.shabbyshackinn;

import com.example.shabbyshackinn.models.AllContractCustomers;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.repos.ContractCustomerRepo;
import com.example.shabbyshackinn.services.ContractCustomerService;
import com.example.shabbyshackinn.services.Impl.ContractCustomerServiceImpl;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {

    ContractCustomerRepo contractCustomerRepo;

    
    public FetchContractCustomers(ContractCustomerRepo contractCustomerRepo){
        this.contractCustomerRepo = contractCustomerRepo;
    }
   
    @Override 
    public void run(String... args) throws Exception {
        System.out.println("Run fetchContractCustomers");
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
        AllContractCustomers allContractCustomers = mapper.readValue(new URL("https://javaintegration.systementor.se/customers"), 
                AllContractCustomers.class);
        
        
        ContractCustomerServiceImpl contractCustomerService = new ContractCustomerServiceImpl(contractCustomerRepo);
        for (ContractCustomer c : allContractCustomers.contractCustomers) {
            contractCustomerService.saveOrUpdateContractCustomer(c);
            System.out.println(c.companyName);
            System.out.println(c.contactName);
            System.out.println(c.country);
            
        }
    }
}
