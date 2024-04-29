package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import com.example.shabbyshackinn.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class SearchController {

    private static final Logger log =
            LoggerFactory.getLogger(SearchController.class);

    private final CustomerService customerService;
    private final BookingService bookingService;
    private final RoomService roomService;

    public SearchController(CustomerService customerService, BookingService bookingService,RoomService roomService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @RequestMapping("/search")
    public String search(Model model, @RequestParam LocalDate startDate,
                         @RequestParam LocalDate endDate, @RequestParam int amountOfPersons){
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("amountOfPersons", amountOfPersons);
        List<DetailedRoomDto> availableRooms = roomService.findAvailableRooms(startDate,endDate,amountOfPersons);
        model.addAttribute("searchResults", availableRooms);
        return "searchResults";
    }
    
    
    @RequestMapping("/createBooking/{id}/{startDate}/{endDate}")
    public String createBooking(Model model, @PathVariable Long id, @PathVariable LocalDate startDate
    ,@PathVariable LocalDate endDate){
        if(id == null){
            return "redirect:/shabbyShackInn/search";
        }
        
        DetailedRoomDto r = roomService.findDetailedRoomById(id);
        model.addAttribute("room", r);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        List<MiniCustomerDto> miniCustomerDtoList = customerService.getallMiniCustomers();
        model.addAttribute("allCustomer", miniCustomerDtoList);
        
        return "createBooking";
    }
    
    @RequestMapping("/finishBooking/{customerId}/{roomId}/{startDate}/{endDate}")
    public String finishBooking(@PathVariable Long customerId, @PathVariable Long roomId,
                                @PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        log.info("Request to add new booking");
        MiniCustomerDto customer = customerService.findMiniCustomerById(customerId);
        MiniRoomDto room = roomService.findMiniRoomById(roomId);
        bookingService.addBooking(new DetailedBookingDto(startDate,endDate,0,0, customer, room));
        return "redirect:/shabbyShackInn/index";
    }
}