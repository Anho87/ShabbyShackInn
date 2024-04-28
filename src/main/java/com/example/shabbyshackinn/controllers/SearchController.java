package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class SearchController {

    private final CustomerService customerService;
    private final BookingService bookingService;
    private final RoomService roomService;

    public SearchController(CustomerService customerService, BookingService bookingService,RoomService roomService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @RequestMapping("/search")
    public String search(Model model, @RequestParam(name = "startDate") LocalDate startDate,
                         @RequestParam(name = "endDate") LocalDate endDate, @RequestParam(name = "amountOfPersons") int amountOfPersons){
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("amountOfPersons", amountOfPersons);
        List<DetailedRoomDto> availableRooms = roomService.findAvailableRooms(startDate,endDate,amountOfPersons);
        model.addAttribute("searchResults", availableRooms);
        return "searchResults";
    }
}