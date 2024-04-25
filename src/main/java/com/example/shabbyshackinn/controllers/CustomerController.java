package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody DetailedCustomerDto customer) {
        return customerService.addCustomer(customer);
    }
    
    @PostMapping("/update")
    public String updateCustomer(@RequestBody DetailedCustomerDto customer){
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
        return customerService.deleteCustomer(id);
    }

    @RequestMapping("/getAll")
    public List<DetailedCustomerDto> getAllCustomers(){
        return customerService.getAllCustomers();
    }
}
