package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    private final CustomerService customerService;
    
    private static final Logger log =
            LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody DetailedCustomerDto customer) {
        log.info("New customer with id:" + customer.getId() + " has been added");
        return customerService.addCustomer(customer);
    }
    
    @PostMapping("/update")
    public String updateCustomer(@RequestBody DetailedCustomerDto customer){
        log.info("Customer with id:" + customer.getId() + " has been updated");
        return customerService.updateCustomer(customer);
    }
    
//    @RequestMapping("/add")
//    public String addCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone
//    ,@RequestParam String eMail){
//        return customerService.addCustomer(new DetailedCustomerDto(firstName,lastName,phone,eMail));
//    }

//    @RequestMapping("/update")
//    public String updateCustomer(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone
//            ,@RequestParam String eMail){
//        return customerService.updateCustomer(new DetailedCustomerDto(id,firstName,lastName,phone,eMail));
//    }
    
    @RequestMapping("/delete")
    public String deleteCustomer(@RequestParam Long id){
        log.info("Customer with id:" + id + " has been deleted");
        return customerService.deleteCustomer(id);
    }

    @RequestMapping("/getAll")
    public List<DetailedCustomerDto> getAllCustomers(){
        log.info("Fetching all customers");
        return customerService.getAllCustomers();
    }
}
