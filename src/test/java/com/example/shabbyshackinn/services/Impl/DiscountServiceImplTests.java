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
import java.time.Month;
import java.time.temporal.ChronoUnit;
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
    void calculateTotalDiscountForCustomerWithSundayDiscountAndLongStayDiscount() {
        LocalDate today = LocalDate.now();

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        // Mock bookingRepo behavior (only 2 nights booked last year)
        when(bookingRepo.sumNightsByCustomerIdAndYear(customerId, today.minusYears(1), today)).thenReturn(Optional.of(2));

        int discountedPrice = service.calculateDiscount(roomId, customerId, detailedBookingDto);

        assertEquals(9910, discountedPrice);
    }

    @Test
    void calculateTotalDiscountForCustomersWithAllDiscounts() {
        LocalDate today = LocalDate.now();

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        // Mock bookingRepo behavior
        when(bookingRepo.sumNightsByCustomerIdAndYear(customerId, today.minusYears(1), today)).thenReturn(Optional.of(14));

        int discountedPrice = service.calculateDiscount(roomId, customerId, detailedBookingDto);

        assertEquals(9710, discountedPrice);
    }

    @Test
    void calculateSundayDiscount() {
        LocalDate sunday8DaysBeforeMonday = LocalDate.of(2024, Month.MAY, 5);
        LocalDate sunday = LocalDate.of(2024, Month.MAY, 12);
        LocalDate monday = LocalDate.of(2024, Month.MAY, 13);
        int totalPriceFor2Sundays = (int) (ChronoUnit.DAYS.between(sunday8DaysBeforeMonday, monday) * price);

        DetailedBookingDto detailedBookingDto2 = new DetailedBookingDto(bookingId, sunday, monday, bookingNumber, extraBedsWanted, price, miniRoomDto);
        DetailedBookingDto detailedBookingDto3 = new DetailedBookingDto(bookingId, sunday8DaysBeforeMonday, monday, bookingNumber, extraBedsWanted, totalPriceFor2Sundays, miniRoomDto);
        DetailedBookingDto detailedBookingDto4 = new DetailedBookingDto(bookingId, sunday.minusDays(1), monday.minusDays(1), bookingNumber, extraBedsWanted, price, miniRoomDto);

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        double noDiscount = service.calculateSundayDiscount(detailedBookingDto4, price);
        double discountedPrice = service.calculateSundayDiscount(detailedBookingDto2, price);
        double discountedPriceFor2Sundays = service.calculateSundayDiscount(detailedBookingDto3, price);

        assertEquals(0, noDiscount);
        assertEquals(20, discountedPrice);
        assertEquals(40, discountedPriceFor2Sundays);
    }

    @Test
    void calculateLongStayDiscount() {
        int totalPrice10Days = (int) (ChronoUnit.DAYS.between(startDate, endDate) * price);
        DetailedBookingDto detailedBookingDto2 = new DetailedBookingDto(bookingId, startDate, endDate, bookingNumber, extraBedsWanted, price, miniRoomDto);
        DetailedBookingDto detailedBookingDto3 = new DetailedBookingDto(bookingId, startDate, endDate.minusDays(9), bookingNumber, extraBedsWanted, totalPrice10Days, miniRoomDto);

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        double discountedPriceFor10Days = service.calculateLongStayDiscount(detailedBookingDto2, totalPrice10Days);
        double noDiscount = service.calculateLongStayDiscount(detailedBookingDto3, price);

        assertEquals(50, discountedPriceFor10Days);
        assertEquals(0, noDiscount);
    }

    @Test
    void calculateLoyaltyDiscount() {
        LocalDate today = LocalDate.now();
        double totalStandardPrice = (int) (ChronoUnit.DAYS.between(startDate, endDate) * price);

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        // Mock bookingRepo behavior
        when(bookingRepo.sumNightsByCustomerIdAndYear(customerId, today.minusYears(1), today)).thenReturn(Optional.of(10));

        double discountedPrice = service.calculateLoyaltyDiscount(roomId, totalStandardPrice);

        assertEquals(200, discountedPrice);
    }

    @Test
    void calculateNoLoyaltyDiscount() {
        LocalDate today = LocalDate.now();
        double totalStandardPrice = (int) (ChronoUnit.DAYS.between(startDate, endDate) * price);

        // Mock roomRepo behavior
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));

        // Mock bookingRepo behavior
        when(bookingRepo.sumNightsByCustomerIdAndYear(customerId, today.minusYears(1), today)).thenReturn(Optional.of(9));

        double discountedPrice = service.calculateLoyaltyDiscount(roomId, totalStandardPrice);

        assertEquals(0, discountedPrice);
    }
}