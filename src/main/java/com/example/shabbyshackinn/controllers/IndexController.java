package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class IndexController {
    
    private final CustomerService customerService;
    private final BookingService bookingService;

    public IndexController(CustomerService customerService, BookingService bookingService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
    }
    
    @RequestMapping("/index")
    public String index(Model model){
        List<MiniCustomerDto> miniCustomerDtoList = customerService.getallMiniCustomers();
        List<MiniBookingDto> miniBookingDtoList = bookingService.getAllCurrentAndFutureMiniBookings();
        model.addAttribute("allCustomer", miniCustomerDtoList);
        model.addAttribute("allBooking", miniBookingDtoList);
        return "index";
    }

    @RequestMapping(path = "/deleteById/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/shabbyShackInn/index";
    }

    @RequestMapping(path = "/deleteBookingById/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/shabbyShackInn/index";
    }
    
    @RequestMapping(path ="/customerAddAndUpdate/{id}")
    public String showCustomerAddAndUpdatePage(@PathVariable Long id, Model model) {
        Customer c = customerService.findCustomerById(id);
        model.addAttribute("id", c.getId());
        model.addAttribute("firstName", c.getFirstName());
        model.addAttribute("lastName", c.getLastName());
        model.addAttribute("phone", c.getPhone());
        model.addAttribute("eMail", c.getEMail());
        return "customerAddAndUpdate";
    }


    @RequestMapping("/search")
    public String search(Model model, @RequestParam(name = "startDate") String startDate, 
                         @RequestParam(name = "endDate") String endDate, @RequestParam(name = "amountOfPersons") int amountOfPersons){
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("amountOfPersons", amountOfPersons);
        //Skickar med datum och antalPersoner till searchResults
        return "searchResults";
    }

    @PostMapping("/updateOrAddCustomer")
    public String updateOrAddCustomer(@RequestParam Long id,@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
                                      @RequestParam String eMail){
        DetailedCustomerDto customerDto = new DetailedCustomerDto(id,firstName,lastName,phone,eMail);
        return customerService.updateCustomer(customerDto);
    }
}
