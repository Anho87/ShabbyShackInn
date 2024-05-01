package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shabbyShackInn")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    
    @RequestMapping(path = "/deleteById/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String feedback =  customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/index";
    }

    @RequestMapping(path = "/customerAddAndUpdate/{id}")
    public String showCustomerAddAndUpdatePage(@PathVariable Long id, Model model) {
        if(id == 0){
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
    public String updateOrAddCustomer(@RequestParam Long id,@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
                                      @RequestParam String eMail, RedirectAttributes redirectAttributes){
        if (id == 0){
            DetailedCustomerDto customerDto = new DetailedCustomerDto(id,firstName,lastName,phone,eMail);
            String feedback = customerService.addCustomer(customerDto);
            redirectAttributes.addFlashAttribute("feedback", feedback);
            return "redirect:/shabbyShackInn/index";
        }
        DetailedCustomerDto customerDto = new DetailedCustomerDto(id,firstName,lastName,phone,eMail);
        String feedback = customerService.updateCustomer(customerDto);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/index";
    }

}
