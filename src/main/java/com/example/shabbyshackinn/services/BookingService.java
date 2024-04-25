package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    
    MiniBookingDto bookingToMiniBookingDto(Booking booking);
    
    DetailedBookingDto bookingToDetailedBookingDto(Booking booking);
    
    Booking detailedBookingDtoToBooking(DetailedBookingDto detailedBookingDto, Customer customer, Room room);
    
    List<DetailedBookingDto> getAllBookings();


    String addBooking(DetailedBookingDto booking);
    String updateBooking(DetailedBookingDto booking);
    String deleteBooking(Long id);
    boolean checkIfBookingPossible(DetailedBookingDto booking);
}
