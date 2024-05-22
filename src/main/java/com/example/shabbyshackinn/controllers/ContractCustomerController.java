package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.models.ContractCustomer;
import com.example.shabbyshackinn.services.ContractCustomerService;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    public ContractCustomerController(ContractCustomerService contractCustomerService) {
        this.contractCustomerService = contractCustomerService;
    }

    @GetMapping("/allContractCustomers")
    public String listAllContractCustomers(Model model) {
        List<MiniContractCustomerDto> contractCustomerList = contractCustomerService.getAllMiniContractCustomers();
        model.addAttribute("allContractCustomer", contractCustomerList);
        return "allContractCustomers";
    }

    @GetMapping("searchedContractCustomers")
    public String searchedContractCustomers(Model model,
                                            @RequestParam(defaultValue = "") String q,
                                            @RequestParam(defaultValue = "companyName") String sortCol,
                                            @RequestParam(defaultValue = "ASC") String sortOrder) {
        q = q.trim();
        System.out.println(q);
        System.out.println(sortCol);
        System.out.println(sortOrder);
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        List<ContractCustomer> searchedContractCustomerList = contractCustomerService.findAllBySearchAndSortOrder(q, sort);
        model.addAttribute("allContractCustomer", searchedContractCustomerList);
        model.addAttribute("q", q);
        return "allContractCustomers";
    }

    @GetMapping("/contractCustomer/{id}")
    public String showContractCustomer(@PathVariable Long id, Model model) {
        DetailedContractCustomerDto contractCustomer = contractCustomerService.findDetailedContractCustomerById(id);
        model.addAttribute("contractCustomer", contractCustomer);
        return "contractCustomer";
    }
}
