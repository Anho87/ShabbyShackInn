package com.example.shabbyshackinn.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "allCustomers")
public class AllContractCustomers {
    @JacksonXmlProperty(localName = "customers")
    public List<ContractCustomer> contractCustomers;
}
