package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class IndexController {
    
    private final CustomerService customerService;

    public IndexController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @RequestMapping("/index")
    public String index(Model model){
        List<DetailedCustomerDto> detailedCustomerDtoList = customerService.getAllCustomers();
        model.addAttribute("allCustomer", detailedCustomerDtoList);
        model.addAttribute("Title", "CustomerList");
        return "index";
    }

    @RequestMapping(path = "/deleteById/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        customerService.deleteCustomer(id);
        return index(model);
    }
}
