package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log =
            LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody DetailedCustomerDto customer) {
        return customerService.addCustomer(customer);
    }
    
    @PostMapping("/update")
    public String updateCustomer(@RequestBody DetailedCustomerDto customer) {
        return customerService.updateCustomer(customer);
    }
    @RequestMapping("/add")
    public String addCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone
    ,@RequestParam String eMail){
        log.info("Customer" + firstName + " has been added");
        return customerService.addCustomer(new DetailedCustomerDto(firstName,lastName,phone,eMail));
    }

    @RequestMapping("/update")
    public String updateCustomer(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone
            ,@RequestParam String eMail){
        log.info("Customer with id:" + id + " has been updated");
        return customerService.updateCustomer(new DetailedCustomerDto(id,firstName,lastName,phone,eMail));
    }

    @RequestMapping("/delete")
    public String deleteCustomer(@RequestParam Long id){
        log.info("Customer with id:" + id + " has been deleted");
        return customerService.deleteCustomer(id);
    }

    @RequestMapping("/getAll")
    public List<DetailedCustomerDto> getAllCustomers(){
        log.info("Get request for all customers");
        return customerService.getAllCustomers();
    }
}
