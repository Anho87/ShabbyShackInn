package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.CustomerService;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class BookingController {
    
    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerService customerService;

    public BookingController(BookingService bookingService,RoomService roomService,CustomerService customerService){
        this.bookingService = bookingService;
        this.roomService= roomService;
        this.customerService = customerService;
    }

    
    @RequestMapping(path = "/deleteBookingById/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/shabbyShackInn/index";
    }

    @RequestMapping(path ="/bookingAddAndUpdate/{id}")
    public String showBookingAddAndUpdatePage(@PathVariable Long id, Model model) {
        if(id == 0){
            return "bookingAddAndUpdate";
        }
        DetailedBookingDto b = bookingService.findDetailedBookingById(id);
        model.addAttribute("id",b.getId());
        model.addAttribute("startDate",b.getStartDate());
        model.addAttribute("endDate",b.getEndDate());
        model.addAttribute("bookingNumber",b.getBookingNumber());
        model.addAttribute("extraBedsWanted",b.getExtraBedsWanted());

        model.addAttribute("roomNumber", b.getMiniRoomDto().getRoomNumber());

        model.addAttribute("firstName", b.getMiniCustomerDto().getFirstName());
        model.addAttribute("lastName", b.getMiniCustomerDto().getLastName());
        model.addAttribute("eMail", b.getMiniCustomerDto().getEMail());
        return "bookingAddAndUpdate";
    }

    @PostMapping("/updateOrAddBooking")
    public String updateOrAddBooking(@RequestParam Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
                                     @RequestParam int extraBedsWanted, @RequestParam int roomNumber){
        MiniRoomDto miniRoomDto = roomService.findMiniRoomByRoomNumber(roomNumber);
        DetailedBookingDto bookingDto = new DetailedBookingDto(id,startDate,endDate, extraBedsWanted,miniRoomDto);
        bookingService.updateBooking(bookingDto);
        return "redirect:/shabbyShackInn/index";
    }

    @RequestMapping("/createBooking/{id}/{startDate}/{endDate}/{amountOfPersons}")
    public String createBooking(Model model, @PathVariable Long id, @PathVariable LocalDate startDate
            ,@PathVariable LocalDate endDate,@PathVariable int amountOfPersons){
        if(id == null){
            return "redirect:/shabbyShackInn/search";
        }

        DetailedRoomDto r = roomService.findDetailedRoomById(id);
        int extraBedsWanted = Math.max(0, amountOfPersons - r.getBeds());
        model.addAttribute("room", r);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("extraBedsWanted", extraBedsWanted);
        List<MiniCustomerDto> miniCustomerDtoList = customerService.getallMiniCustomers();
        model.addAttribute("allCustomer", miniCustomerDtoList);

        return "createBooking";
    }

    @RequestMapping("/finishBooking/{customerId}/{roomId}/{startDate}/{endDate}/{extraBedsWanted}")
    public String finishBooking(@PathVariable Long customerId, @PathVariable Long roomId,
                                @PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable int extraBedsWanted){
        MiniCustomerDto customer = customerService.findMiniCustomerById(customerId);
        MiniRoomDto room = roomService.findMiniRoomById(roomId);
        bookingService.addBooking(new DetailedBookingDto(startDate,endDate,0,extraBedsWanted, customer, room));
        return "redirect:/shabbyShackInn/index";
    }

}
