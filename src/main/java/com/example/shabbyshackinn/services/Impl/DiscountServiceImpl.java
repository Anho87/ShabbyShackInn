package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.DiscountService;

public class DiscountServiceImpl implements DiscountService {

    final private BookingRepo bookingRepo;
    final private CustomerRepo customerRepo;
    final private RoomRepo roomRepo;

    public DiscountServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo, RoomRepo roomRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }

}
