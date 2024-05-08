package com.example.shabbyshackinn;

import com.example.shabbyshackinn.models.AllContractCustomers;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomers implements CommandLineRunner {
    
    @Override 
    public void run(String... args) throws Exception {
        System.out.println("Run fetchContractCustomers");
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
        AllContractCustomers allContractCustomers = mapper.readValue(new URL("https://javaintegration.systementor.se/customers"), 
                AllContractCustomers.class);
        
        for (ContractCustomer c : allContractCustomers.contractCustomers) {
            System.out.println(c.companyName);
            System.out.println(c.contactName);
            System.out.println(c.country);
            
        }
    }
}
