package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class IndexController {
    
    private final CustomerService customerService;
    private final BookingService bookingService;
    private final RoomService roomService;

    public IndexController(CustomerService customerService, BookingService bookingService,RoomService roomService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.roomService = roomService;
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
        if(id == 0){
            return "customerAddAndUpdate";
        }
        Customer c = customerService.findCustomerById(id);
        model.addAttribute("id", c.getId());
        model.addAttribute("firstName", c.getFirstName());
        model.addAttribute("lastName", c.getLastName());
        model.addAttribute("phone", c.getPhone());
        model.addAttribute("eMail", c.getEMail());
        return "customerAddAndUpdate";
    }

    @RequestMapping(path ="/bookingAddAndUpdate/{id}")
    public String showBookingAddAndUpdatePage(@PathVariable Long id, Model model) {
        if(id == 0){
            return "bookingAddAndUpdate";
        }
        Booking b = bookingService.findBookingById(id);
        model.addAttribute("id",b.getId());
        model.addAttribute("startDate",b.getStartDate());
        model.addAttribute("endDate",b.getEndDate());
        model.addAttribute("bookingNumber",b.getBookingNumber());
        model.addAttribute("extraBedsWanted",b.getExtraBedsWanted());
        
        model.addAttribute("roomNumber", b.getRoom().getRoomNumber());
        
        model.addAttribute("firstName", b.getCustomer().getFirstName());
        model.addAttribute("lastName", b.getCustomer().getLastName());
        model.addAttribute("eMail", b.getCustomer().getEMail());
        return "bookingAddAndUpdate";
    }


    /*@RequestMapping("/search")
    public String search(Model model, @RequestParam(name = "startDate") String startDate, 
                         @RequestParam(name = "endDate") String endDate, @RequestParam(name = "amountOfPersons") int amountOfPersons){
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("amountOfPersons", amountOfPersons);
        //Skickar med datum och antalPersoner till searchResults
        return "searchResults";
    }*/

    @PostMapping("/updateOrAddCustomer")
    public String updateOrAddCustomer(@RequestParam Long id,@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phone,
                                      @RequestParam String eMail){
        DetailedCustomerDto customerDto = new DetailedCustomerDto(id,firstName,lastName,phone,eMail);
        customerService.updateCustomer(customerDto);
        return "redirect:/shabbyShackInn/index";
    }

    @PostMapping("/updateOrAddBooking")
    public String updateOrAddBooking(@RequestParam Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
                                     @RequestParam int extraBedsWanted, @RequestParam int roomNumber){
        MiniRoomDto miniRoomDto = roomService.findMiniRoomByRoomNumber(roomNumber);
        DetailedBookingDto bookingDto = new DetailedBookingDto(id,startDate,endDate, extraBedsWanted,miniRoomDto);
        bookingService.updateBooking(bookingDto);
        return "redirect:/shabbyShackInn/index";
    }
}
