package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.services.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    
    private final BookingService bookingService;

    private static final Logger log =
            LoggerFactory.getLogger(BookingController.class);
    
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
    @PostMapping("add")
    public String addBooking(@RequestBody DetailedBookingDto booking){
        log.info("New booking with id:{} added", booking.getId());
        return bookingService.addBooking(new DetailedBookingDto(booking.getStartDate(),booking.getEndDate(),booking.getBookingNumber()
        ,booking.getExtraBedsWanted(),booking.getMiniCustomerDto(),booking.getMiniRoomDto()));
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
