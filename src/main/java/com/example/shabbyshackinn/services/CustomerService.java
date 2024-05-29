package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.models.Customer;

import java.util.List;


public interface CustomerService {

    DetailedCustomerDto customerToDetailedCustomerDTO(Customer customer);

    Customer detailedCustomerToCustomer(DetailedCustomerDto customer);

    MiniCustomerDto customerToMiniCustomerDto(Customer customer);

    List<DetailedCustomerDto> getAllCustomers();

    String addCustomer(DetailedCustomerDto customer);

    String updateCustomer(DetailedCustomerDto customer);

    String deleteCustomer(Long id);

    List<MiniCustomerDto> getallMiniCustomers();

    MiniCustomerDto findMiniCustomerById(Long id);

    DetailedCustomerDto findDetailedCustomerDtoById(Long id);

    Boolean customerHasActiveBookings(Customer customer);
}
