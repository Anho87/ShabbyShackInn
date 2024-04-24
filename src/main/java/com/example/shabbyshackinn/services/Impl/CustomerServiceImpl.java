package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public List<DetailedCustomerDto> getAllCustomers() {
        return customerRepo.findAll().stream().map(c -> customerToDetailedCustomerDTO(c)).toList();
    }

    @Override
    public DetailedCustomerDto customerToDetailedCustomerDTO(Customer customer) {
        return DetailedCustomerDto.builder().id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).phone(customer.getPhone())
                .eMail(customer.getEMail()).build();
    }

    @Override
    public String addCustomer(DetailedCustomerDto customer) {
        customerRepo.save(detailedCustomerToCustomer(customer));
        return "Customer " + customer.getFirstName() + " is saved";
    }
    
    @Override
    public String updateCustomer(DetailedCustomerDto customer){
        customerRepo.save(detailedCustomerToCustomer(customer));
        return "Customer " + customer.getFirstName() + " is updated";
    }

    @Override
    public Customer detailedCustomerToCustomer(DetailedCustomerDto c) {
        return Customer.builder().id(c.getId()).firstName(c.getFirstName()).lastName(c.getLastName())
                .phone(c.getPhone()).eMail(c.getEMail()).build();
    }

    @Override
    public MiniCustomerDto customerToMiniCustomerDto(Customer customer) {
        return MiniCustomerDto.builder().id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).build();
    }
    
    @Override
    public String deleteCustomer(Long id){
        customerRepo.deleteById(id);
        return "Customer deleted";
    }
}


