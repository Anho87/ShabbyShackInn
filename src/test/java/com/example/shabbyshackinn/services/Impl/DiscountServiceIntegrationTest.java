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
import java.util.Optional;

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
        Customer customer = new Customer(1L, "John", "Doe", "123456789", "john.doe@example.com");
        Customer customer2 = new Customer(2L, "John", "Doe", "123456789", "john.doe@example.com");
        Room room = new Room(1L, RoomType.DOUBLE, 1, 2, 2000, 1);

        customerRepo.save(customer);
        customerRepo.save(customer2);
        roomRepo.save(room);

        LocalDate startDate1 = LocalDate.now().minusDays(20);
        LocalDate endDate1 = LocalDate.now().minusDays(10);
        Booking booking1 = new Booking(1L, startDate1, endDate1, 123, 1, 999999999, customer, room);

        LocalDate startDate2 = LocalDate.now().minusDays(50);
        LocalDate endDate2 = LocalDate.now().minusDays(40);
        Booking booking2 = new Booking(2L, startDate2, endDate2, 124, 1, 999999999, customer, room);

        bookingRepo.save(booking1);
        bookingRepo.save(booking2);
    }

    @Test
    void testSumNightsByCustomerIdAndYear() {
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1);

        Optional<Integer> nightsBooked20 = bookingRepo.sumNightsByCustomerIdAndYear(1L, oneYearAgo, today);
        Optional<Integer> nightsBooked0 = bookingRepo.sumNightsByCustomerIdAndYear(2L, oneYearAgo, today);

        int nightsBookedLastYear20 = nightsBooked20.orElse(0);
        int nightsBookedLastYear0 = nightsBooked0.orElse(0);

        assertEquals(20, nightsBookedLastYear20);
        assertEquals(0, nightsBookedLastYear0);
    }
}
