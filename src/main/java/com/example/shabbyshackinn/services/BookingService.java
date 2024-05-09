package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;

import java.util.List;

public interface BookingService {
    
    MiniBookingDto bookingToMiniBookingDto(Booking booking);
    DetailedBookingDto bookingToDetailedBookingDto(Booking booking);
    Booking detailedBookingDtoToBooking(DetailedBookingDto detailedBookingDto, Customer customer, Room room);
    List<MiniBookingDto> getAllMiniBookings();
    List<MiniBookingDto> getAllCurrentAndFutureMiniBookings();
    String addBooking(DetailedBookingDto booking);
    String updateBooking(DetailedBookingDto booking);
    String deleteBooking(Long id);
    boolean checkIfBookingPossible(DetailedBookingDto booking);
    DetailedBookingDto findDetailedBookingById(Long id);
    boolean isCustomerOkInBlacklist(String eMail);
}
