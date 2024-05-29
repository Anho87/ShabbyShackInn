package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
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
    public String index(Model model) {
        List<MiniCustomerDto> miniCustomerDtoList = customerService.getallMiniCustomers();
        List<MiniBookingDto> miniBookingDtoList = bookingService.getAllCurrentAndFutureMiniBookings();
        model.addAttribute("allCustomer", miniCustomerDtoList);
        model.addAttribute("allBooking", miniBookingDtoList);
        return "index";
    }

}
