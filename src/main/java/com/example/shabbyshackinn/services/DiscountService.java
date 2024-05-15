package com.example.shabbyshackinn.services;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;

public interface DiscountService {
    int calculateDiscount (Long roomId, Long customerId, DetailedBookingDto detailedBookingDto);
}
