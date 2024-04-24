package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.models.Customer;

import java.util.List;


public interface CustomerService {

    DetailedCustomerDto customerToDetailedCustomerDTO(Customer customer);
    List<DetailedCustomerDto> getAllCustomers();
    String addCustomer(DetailedCustomerDto customer);
    Customer detailedCustomerToCustomer(DetailedCustomerDto customer);
    MiniCustomerDto customerToMiniCustomerDto(Customer customer);
    String updateCustomer(DetailedCustomerDto customer);
    String deleteCustomer(Long id);
}
