package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.repos.CustomerRepo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerRepo customerRepo;

    public CustomerController(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    @RequestMapping("/getAllCustomers")
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }
}
