package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/shabbyShackInn")
public class SearchController {

    private final CustomerService customerService;
    private final BookingService bookingService;

    public SearchController(CustomerService customerService, BookingService bookingService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
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
}