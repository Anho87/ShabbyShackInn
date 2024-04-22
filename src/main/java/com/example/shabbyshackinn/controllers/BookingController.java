package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;

    public BookingController(BookingRepo bookingRepo, CustomerRepo customerRepo, RoomRepo roomRepo){
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }

    @RequestMapping("/getAllBookings")
    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }

    @PostMapping("/addBooking")
    public List<Booking> addBooking(@RequestBody Booking booking){
        bookingRepo.save(booking);
        return bookingRepo.findAll();
    }

    @RequestMapping("/addNewBooking")
    public List<Booking> addNewBooking(@RequestParam Long customerId
            , @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @RequestParam int bookingNumber
            , @RequestParam int extraBedsWanted, @RequestParam Long roomId){
        Customer customer = customerRepo.findById(customerId).get();
        Room room = roomRepo.findById(roomId).get();
        if(customer != null && room != null){
            Booking newBooking = new Booking(customer,startDate,endDate,bookingNumber, extraBedsWanted,room);
            bookingRepo.save(newBooking);
        }
        return bookingRepo.findAll();
    }

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
