package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingServiceImplIntegrationTest {

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private BookingService bookingService;

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

        LocalDate startDate3 = LocalDate.now().plusDays(1);
        LocalDate endDate3 = LocalDate.now().plusDays(10);
        Booking booking3 = new Booking(3L, startDate3, endDate3, 125, 1, 20000, customer2, room);

        LocalDate startDate4 = LocalDate.now().plusDays(5);
        LocalDate endDate4 = LocalDate.now().plusDays(15);
        Booking booking4 = new Booking(4L, startDate4, endDate4, 126, 1, 25000, customer2, room);

        bookingRepo.save(booking1);
        bookingRepo.save(booking2);
        bookingRepo.save(booking3);
        bookingRepo.save(booking4);
    }

    @Test
    void testFindDetailedBookingById() {
        DetailedBookingDto booking = bookingService.findDetailedBookingById(1L);
        assertNotNull(booking);
        assertEquals(123, booking.getBookingNumber());
    }

    @Test
    void testGetAllMiniBookings() {
        List<MiniBookingDto> bookings = bookingService.getAllMiniBookings();
        assertNotNull(bookings);
        assertEquals(4, bookings.size());
    }


    @Test
    void testGetAllCurrentAndFutureMiniBookings() {
        List<MiniBookingDto> bookings = bookingService.getAllCurrentAndFutureMiniBookings();
        assertNotNull(bookings);
        assertEquals(2, bookings.size());

        LocalDate today = LocalDate.now();
        for (MiniBookingDto booking : bookings) {
            assertTrue(booking.getEndDate().isAfter(today));
        }
    }

    @Test
    void testUpdateBooking() {
        DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder()
                .id(1L)
                .startDate(LocalDate.now().minusDays(12))
                .endDate(LocalDate.now().minusDays(2))
                .bookingNumber(123)
                .extraBedsWanted(5)
                .totalPrice(18000)
                .miniCustomerDto(new MiniCustomerDto(1L, "John", "Doe", "john.doe@example.com"))
                .miniRoomDto(new MiniRoomDto(1L, RoomType.DOUBLE, 1))
                .build();

        String response = bookingService.updateBooking(detailedBookingDto);
        assertEquals("Booking updated", response);

        Booking updatedBooking = bookingRepo.findById(1L).get();
        assertEquals(5, updatedBooking.getExtraBedsWanted());
    }

    @Test
    void testDeleteBooking() {
        String response = bookingService.deleteBooking(1L);
        assertEquals("Booking deleted", response);

        List<Booking> bookings = bookingRepo.findAll();
        assertEquals(3, bookings.size());
    }

    @Test
    void testFindBookingByDates() {
        LocalDate startDate = LocalDate.now().minusDays(20);
        LocalDate endDate = LocalDate.now().minusDays(5);

        List<DetailedBookingDto> bookings = bookingService.findBookingByDates(startDate, endDate);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    void testCheckIfBookingPossible() {
        DetailedBookingDto detailedBookingDtoPossible = DetailedBookingDto.builder()
                .id(3L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .bookingNumber(126)
                .extraBedsWanted(0)
                .miniCustomerDto(new MiniCustomerDto(1L, "John", "Doe", "john.doe@example.com"))
                .miniRoomDto(new MiniRoomDto(1L, RoomType.DOUBLE, 1))
                .totalPrice(1000)
                .build();

        boolean bookingIsPossible = bookingService.checkIfBookingPossible(detailedBookingDtoPossible);
        assertTrue(bookingIsPossible);

        DetailedBookingDto detailedBookingDtoNotPossible = DetailedBookingDto.builder()
                .id(4L)
                .startDate(LocalDate.now().minusDays(8))  // Overlaps with booking1
                .endDate(LocalDate.now().minusDays(6))
                .bookingNumber(127)
                .extraBedsWanted(0)
                .miniCustomerDto(new MiniCustomerDto(1L, "John", "Doe", "john.doe@example.com"))
                .miniRoomDto(new MiniRoomDto(1L, RoomType.DOUBLE, 1))
                .totalPrice(1000)
                .build();

        boolean bookingIsNotPossible = bookingService.checkIfBookingPossible(detailedBookingDtoNotPossible);
        assertFalse(bookingIsNotPossible);
    }
}
