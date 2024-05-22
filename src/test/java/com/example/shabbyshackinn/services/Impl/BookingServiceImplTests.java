package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.models.*;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BlacklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingServiceImplTests {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private BlacklistService blacklistService;

    @Mock
    private DiscountServiceImpl discountService;

    @InjectMocks
    private BookingServiceImpl service;

    private Customer customer;
    private Room room;
    private Booking booking;
    private DetailedBookingDto detailedBookingDto;
    private MiniRoomDto miniRoomDto;
    private MiniCustomerDto miniCustomerDto;
    private BlacklistResponse blacklistResponseNotBlacklisted;
    private BlacklistResponse blacklistResponseBlacklisted;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "John", "Doe", "123456789", "john.doe@example.com");
        room = new Room(1L, RoomType.DOUBLE, 1, 2, 1000, 1);
        booking = new Booking(1L, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 123, 1, 3000, customer, room);
        miniRoomDto = new MiniRoomDto(1L, RoomType.DOUBLE, 1);
        miniCustomerDto  = new MiniCustomerDto(1L, "John", "Doe", "john.doe@example.com");
        detailedBookingDto = DetailedBookingDto.builder()
                .id(1L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .bookingNumber(123)
                .extraBedsWanted(0)
                .miniCustomerDto(miniCustomerDto)
                .miniRoomDto(miniRoomDto)
                .totalPrice(1000)
                .build();
        blacklistResponseNotBlacklisted = new BlacklistResponse("OK", true);
        blacklistResponseBlacklisted = new BlacklistResponse("Blacklisted", false);
    }

    @Test
    void addBooking() {
        when(bookingRepo.save(booking)).thenReturn(booking);
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(blacklistService.checkIfEmailIsBlacklisted(customer.getEMail())).thenReturn(blacklistResponseNotBlacklisted);

        String feedback = service.addBooking(detailedBookingDto);

        assertEquals("Booking added", feedback);
    }

    @Test
    void getMiniAllBookings() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));

        List<MiniBookingDto> allCustomers = service.getAllMiniBookings();

        assertEquals(1, allCustomers.size());
    }

    @Test
    void bookingToMiniBookingDto() {
        MiniBookingDto actual = service.bookingToMiniBookingDto(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());

        assertEquals(actual.getMiniCustomerDto().getId(), booking.getCustomer().getId());
        assertEquals(actual.getMiniCustomerDto().getFirstName(), booking.getCustomer().getFirstName());
        assertEquals(actual.getMiniCustomerDto().getLastName(), booking.getCustomer().getLastName());
        assertEquals(actual.getMiniCustomerDto().getEMail(), booking.getCustomer().getEMail());

        assertEquals(actual.getMiniRoomDto().getId(), booking.getRoom().getId());
        assertEquals(actual.getMiniRoomDto().getRoomType().roomType, booking.getRoom().getRoomType().roomType);
        assertEquals(actual.getMiniRoomDto().getRoomNumber(), booking.getRoom().getRoomNumber());
    }

    @Test
    void bookingToDetailedBookingDto() {
        DetailedBookingDto actual = service.bookingToDetailedBookingDto(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getBookingNumber(), booking.getBookingNumber());
        assertEquals(actual.getExtraBedsWanted(), booking.getExtraBedsWanted());
        assertEquals(actual.getTotalPrice(), booking.getTotalPrice());

        assertEquals(actual.getMiniCustomerDto().getId(), booking.getCustomer().getId());
        assertEquals(actual.getMiniCustomerDto().getFirstName(), booking.getCustomer().getFirstName());
        assertEquals(actual.getMiniCustomerDto().getLastName(), booking.getCustomer().getLastName());
        assertEquals(actual.getMiniCustomerDto().getEMail(), booking.getCustomer().getEMail());

        assertEquals(actual.getMiniRoomDto().getId(), booking.getRoom().getId());
        assertEquals(actual.getMiniRoomDto().getRoomType().roomType, booking.getRoom().getRoomType().roomType);
        assertEquals(actual.getMiniRoomDto().getRoomNumber(), booking.getRoom().getRoomNumber());
    }

    @Test
    void detailedBookingDtoToBooking() {
        Booking actual = service.detailedBookingDtoToBooking(detailedBookingDto, customer, room);

        assertEquals(actual.getId(), detailedBookingDto.getId());
        assertEquals(actual.getStartDate(), detailedBookingDto.getStartDate());
        assertEquals(actual.getEndDate(), detailedBookingDto.getEndDate());
        assertEquals(actual.getBookingNumber(), detailedBookingDto.getBookingNumber());
        assertEquals(actual.getExtraBedsWanted(), detailedBookingDto.getExtraBedsWanted());
        assertEquals(actual.getTotalPrice(), detailedBookingDto.getTotalPrice());

        assertEquals(actual.getCustomer().getId(), detailedBookingDto.getMiniCustomerDto().getId());
        assertEquals(actual.getCustomer().getFirstName(), detailedBookingDto.getMiniCustomerDto().getFirstName());
        assertEquals(actual.getCustomer().getLastName(), detailedBookingDto.getMiniCustomerDto().getLastName());
        assertEquals(actual.getCustomer().getEMail(), detailedBookingDto.getMiniCustomerDto().getEMail());

        assertEquals(actual.getRoom().getId(), detailedBookingDto.getMiniRoomDto().getId());
        assertEquals(actual.getRoom().getRoomType().roomType, detailedBookingDto.getMiniRoomDto().getRoomType().roomType);
        assertEquals(actual.getRoom().getRoomNumber(), detailedBookingDto.getMiniRoomDto().getRoomNumber());
    }

    @Test
    void getAllCurrentAndFutureMiniBookings() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(bookingRepo.findAllByEndDateAfter(yesterday)).thenReturn(Collections.singletonList(booking));

        List<MiniBookingDto> allCurrentAndFutureMiniBookings = service.getAllCurrentAndFutureMiniBookings();

        assertEquals(1, allCurrentAndFutureMiniBookings.size());
    }

    @Test
    void updateBooking() {
        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));

        String feedback = service.updateBooking(detailedBookingDto);

        assertEquals("Booking updated", feedback);
    }

    @Test
    void deleteBooking() {
        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(blacklistService.checkIfEmailIsBlacklisted(customer.getEMail())).thenReturn(blacklistResponseBlacklisted);

        service.addBooking(detailedBookingDto);
        String feedback = service.deleteBooking(1L);

        assertEquals("Booking deleted", feedback);
    }

    @Test
    void checkIfBookingPossible() {
        List<Booking> conflictingBookings = Collections.singletonList(booking);
        when(bookingRepo.findAllByIdIsNotAndRoomIdAndStartDateIsBeforeAndEndDateIsAfter(
                1L,
                1L,
                LocalDate.now().plusDays(3),  // End date of the new booking
                LocalDate.now().plusDays(1)   // Start date of the new booking
        )).thenReturn(conflictingBookings);

        when(bookingRepo.findAllByIdIsNotAndRoomIdAndStartDateIsBeforeAndEndDateIsAfter(
                2L,
                1L,
                LocalDate.now().plusDays(3),  // End date of the new booking
                LocalDate.now().plusDays(1)   // Start date of the new booking
        )).thenReturn(conflictingBookings);

        when(bookingRepo.findAllByIdIsNotAndRoomIdAndStartDateIsBeforeAndEndDateIsAfter(
                3L,
                1L,
                LocalDate.now().plusDays(3),  // End date of the new booking
                LocalDate.now().plusDays(1)   // Start date of the new booking
        )).thenReturn(conflictingBookings);

        DetailedBookingDto differentBookingIdSameDatesSameRoom = DetailedBookingDto.builder().id(2L)
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).bookingNumber(13).extraBedsWanted(0)
                .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(2000).build();

        DetailedBookingDto differentBookingIdDifferentDates = DetailedBookingDto.builder().id(3L)
                .startDate(LocalDate.now().plusDays(5)).endDate(LocalDate.now().plusDays(6)).bookingNumber(12).extraBedsWanted(0)
                .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(1000).build();

        boolean feedbackSameBooking = service.checkIfBookingPossible(detailedBookingDto);
        boolean feedbackDifferentBookingIdSameDatesSameRoom = service.checkIfBookingPossible(differentBookingIdSameDatesSameRoom);
        boolean feedbackDifferentBookingIdDifferentDates = service.checkIfBookingPossible(differentBookingIdDifferentDates);

        assertFalse(feedbackSameBooking);
        assertFalse(feedbackDifferentBookingIdSameDatesSameRoom);
        assertTrue(feedbackDifferentBookingIdDifferentDates);
    }

    @Test
    void findDetailedBookingById() {
        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));

        DetailedBookingDto actual = service.findDetailedBookingById(booking.getId());

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getBookingNumber(), booking.getBookingNumber());
        assertEquals(actual.getExtraBedsWanted(), booking.getExtraBedsWanted());
        assertEquals(actual.getTotalPrice(), booking.getTotalPrice());

        assertEquals(actual.getMiniCustomerDto().getId(), booking.getCustomer().getId());
        assertEquals(actual.getMiniCustomerDto().getFirstName(), booking.getCustomer().getFirstName());
        assertEquals(actual.getMiniCustomerDto().getLastName(), booking.getCustomer().getLastName());
        assertEquals(actual.getMiniCustomerDto().getEMail(), booking.getCustomer().getEMail());

        assertEquals(actual.getMiniRoomDto().getId(), booking.getRoom().getId());
        assertEquals(actual.getMiniRoomDto().getRoomType(), booking.getRoom().getRoomType());
        assertEquals(actual.getMiniRoomDto().getRoomNumber(), booking.getRoom().getRoomNumber());
    }
}