package com.example.shabbyshackinn;

import com.example.shabbyshackinn.models.AllContractCustomers;
import com.example.shabbyshackinn.models.BlackListedCustomer;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.util.List;


@ComponentScan
public class FetchBlacklist implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Run FetchBlacklist");
        ObjectMapper mapper = new JsonMapper();
        List<BlackListedCustomer> blackListedCustomers = mapper.readValue(new URL("https://javabl.systementor.se/api/stefan/blacklist"),
                mapper.getTypeFactory().constructCollectionType(List.class, BlackListedCustomer.class));

        for (BlackListedCustomer b : blackListedCustomers) {
            System.out.println(b.email);
            System.out.println(b.ok);

        }
    }
}
