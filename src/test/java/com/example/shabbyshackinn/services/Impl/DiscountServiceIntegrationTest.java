package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class DiscountServiceIntegrationTest {

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RoomRepo roomRepo;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer(1L, "John", "Doe", "111111111", "john.doe@example.com");
        Customer customer2 = new Customer(2L, "Alex", "Johnson", "222222222", "alex.johnson@example.com");
        Room room = new Room(1L, RoomType.DOUBLE, 1, 2, 2000, 1);

        customerRepo.save(customer);
        customerRepo.save(customer2);
        roomRepo.save(room);

        LocalDate startDate1 = LocalDate.now().minusDays(10);
        LocalDate endDate1 = LocalDate.now().minusDays(1);
        Booking booking1 = new Booking(1L, startDate1, endDate1, 123, 1, 18000, customer, room);

        LocalDate startDate2 = LocalDate.now().minusDays(365);
        LocalDate endDate2 = LocalDate.now().minusDays(354);
        Booking booking2 = new Booking(2L, startDate2, endDate2, 124, 1, 22000, customer, room);

        bookingRepo.save(booking1);
        bookingRepo.save(booking2);
    }

//    @Test
//    void testSumNightsByCustomerIdAndYear() {
//        LocalDate today = LocalDate.now();
//        LocalDate oneYearAgo = today.minusYears(1);
//
//        int expecting20NightsBookedLastYear = bookingRepo.sumNightsByCustomerIdAndYear(1L, oneYearAgo, today).orElse(0) ;
//        int expecting0NightsBookedLastYear = bookingRepo.sumNightsByCustomerIdAndYear(2L, oneYearAgo, today).orElse(0);
//
//        assertEquals(20, expecting20NightsBookedLastYear);
//        assertEquals(0, expecting0NightsBookedLastYear);
//    }
}
