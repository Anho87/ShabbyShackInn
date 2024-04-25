package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }
    
    @RequestMapping("/getAll")
    public List<DetailedBookingDto> getAllBookings(){
        return bookingService.getAllBookings();
    }
    
    @RequestMapping("/delete")
    public String deleteBooking(@RequestParam Long id){
        return bookingService.deleteBooking(id);
    }
    @PostMapping("/update")
    public String updateBooking(@RequestBody DetailedBookingDto booking){
        return bookingService.updateBooking(booking);
    }
    
//    @RequestMapping("/update")
//    public String updateBooking(@RequestParam Long id,@RequestParam LocalDate startDate, @RequestParam LocalDate endDate
//    ,@RequestParam int extraBedsWanted, @RequestParam MiniRoomDto miniRoomDto){
//        return bookingService.updateBooking(new DetailedBookingDto(id,startDate,endDate,extraBedsWanted,miniRoomDto));
//    }
    
//
//    @PostMapping("/addBooking")
//    public List<Booking> addBooking(@RequestBody Booking booking){
//        bookingRepo.save(booking);
//        return bookingRepo.findAll();
//    }
//
//    @RequestMapping("/addNewBooking")
//    public List<Booking> addNewBooking(@RequestParam Long customerId
//            , @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int bookingNumber
//            , @RequestParam int extraBedsWanted, @RequestParam Long roomId){
//        Customer customer = customerRepo.findById(customerId).get();
//        Room room = roomRepo.findById(roomId).get();
//        if(customer != null && room != null){
//            Booking newBooking = new Booking(customer,startDate,endDate,bookingNumber, extraBedsWanted,room);
//            bookingRepo.save(newBooking);
//        }
//        return bookingRepo.findAll();
//    }

//    @RequestMapping("/addRoomToBooking")
//    public List<Booking> addRoomToBooking(@RequestParam Long bookingId, @RequestParam Long roomId){
//        Booking booking = bookingRepo.findById(bookingId).get();
//        Room room = roomRepo.findById(roomId).get();
//        if (booking != null && room != null){
//            booking.addRoom(room);
//            bookingRepo.save(booking);
//        }
//        return bookingRepo.findAll();
//    }
}
