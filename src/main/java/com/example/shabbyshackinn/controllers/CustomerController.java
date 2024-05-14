package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedContractCustomerDto;
import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniContractCustomerDto;
import com.example.shabbyshackinn.services.ContractCustomerService;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class CustomerController {
    private final CustomerService customerService;
    private final ContractCustomerService contractCustomerService;

    public CustomerController(CustomerService customerService, ContractCustomerService contractCustomerService) {
        this.customerService = customerService;
        this.contractCustomerService = contractCustomerService;
    }


    @RequestMapping(path = "/deleteById/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String feedback = customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/index";
    }

    @RequestMapping(path = "/customerAddAndUpdate/{id}")
    public String showCustomerAddAndUpdatePage(@PathVariable Long id, Model model) {
        if (id == 0) {
            return "customerAddAndUpdate";
        }
        DetailedCustomerDto c = customerService.findDetailedCustomerDtoById(id);
        model.addAttribute("id", c.getId());
        model.addAttribute("firstName", c.getFirstName());
        model.addAttribute("lastName", c.getLastName());
        model.addAttribute("phone", c.getPhone());
        model.addAttribute("eMail", c.getEMail());
        return "customerAddAndUpdate";
    }

    @PostMapping("/updateOrAddCustomer")
    public String updateOrAddCustomer(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
                                      @RequestParam String eMail, RedirectAttributes redirectAttributes) {
        if (id == 0) {
            DetailedCustomerDto customerDto = new DetailedCustomerDto(id, firstName, lastName, phone, eMail);
            String feedback = customerService.addCustomer(customerDto);
            redirectAttributes.addFlashAttribute("feedback", feedback);
            return "redirect:/shabbyShackInn/index";
        }
        DetailedCustomerDto customerDto = new DetailedCustomerDto(id, firstName, lastName, phone, eMail);
        String feedback = customerService.updateCustomer(customerDto);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/index";
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
        List<MiniContractCustomerDto> searchedContractCustomerList = contractCustomerService.findAllBySearchAndSortOrder(q, sort);
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
