package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class DiscountServiceImplTests {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;


    @InjectMocks
    private DiscountServiceImpl service;

    Long customerId = 1L;
    String firstName = "John";
    String lastName = "Doe";
    String phone = "123456789";
    String email = "john.doe@example.com";

    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(10);
    int bookingNumber = 123;
    int extraBedsWanted = 1;
    int totalPrice = 999999999;
    Long roomId = 1L;
    int roomNumber = 1;
    RoomType roomType = RoomType.DOUBLE;
    int beds = 2;
    int price = 1000;
    int possibleExtraBeds = 1;
    List<Booking> bookings = new ArrayList<>();
    Long bookingId = 1L;
    Room room = new Room(roomId, roomType, roomNumber, beds, price, possibleExtraBeds);
    Customer customer = new Customer(customerId, firstName, lastName, phone, email, bookings);
    Booking booking = new Booking(bookingId, startDate, endDate, bookingNumber, extraBedsWanted, totalPrice, customer, room);

    MiniRoomDto miniRoomDto = new MiniRoomDto(roomId, roomType, roomNumber);
    MiniCustomerDto miniCustomerDto = new MiniCustomerDto(customerId, firstName, lastName, email);

    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice).build();


    @Test
    void calculateDiscount() {
        LocalDate today = LocalDate.now();

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        // Mock bookingRepo behavior (only 2 nights booked last year)
        when(bookingRepo.sumNightsByCustomerIdAndYear(customerId, today.minusYears(1), today)).thenReturn(Optional.of(2));

        int discountedPrice = service.calculateDiscount(roomId, customerId, detailedBookingDto);
        System.out.println(room.toString());

        assertEquals(9910, discountedPrice);
    }

    @Test
    void calculateDiscountForCustomersWithMoreThan10nightsBookedLastYear() {
        LocalDate today = LocalDate.now();

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        // Mock bookingRepo behavior
        when(bookingRepo.sumNightsByCustomerIdAndYear(customerId, today.minusYears(1), today)).thenReturn(Optional.of(14));

        int discountedPrice = service.calculateDiscount(roomId, customerId, detailedBookingDto);

        assertEquals(9710, discountedPrice);
    }

}