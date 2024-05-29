package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.email.EmailService;
import com.example.shabbyshackinn.models.*;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BlacklistService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private EmailService emailService;

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
        miniCustomerDto = new MiniCustomerDto(1L, "John", "Doe", "john.doe@example.com");
        detailedBookingDto = DetailedBookingDto.builder()
                .id(1L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .bookingNumber(123)
                .extraBedsWanted(0)
                .totalPrice(1000)
                .miniCustomerDto(miniCustomerDto)
                .miniRoomDto(miniRoomDto)
                .build();
        blacklistResponseNotBlacklisted = new BlacklistResponse("OK", true);
        blacklistResponseBlacklisted = new BlacklistResponse("Blacklisted", false);
    }

    @Test
    void addBooking() {
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(blacklistService.checkIfEmailIsBlacklisted(customer.getEMail())).thenReturn(blacklistResponseNotBlacklisted);
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("bookingConfiramtionEmail.html"), any(org.thymeleaf.context.Context.class))).thenReturn("Email content");

        String feedback = service.addBooking(detailedBookingDto);

        assertEquals("Booking added", feedback);

        // Verify that the email service is called correctly
        verify(emailService).sendBookingConfirmedEmail(
                eq(customer.getEMail()),
                eq("Booking Confirmation"),
                eq(customer.getFirstName()),
                eq(customer.getLastName()),
                eq(detailedBookingDto.getBookingNumber()),
                eq(detailedBookingDto.getStartDate().toString()),
                eq(detailedBookingDto.getEndDate().toString()),
                eq(detailedBookingDto.getTotalPrice())
        );
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

        boolean feedbackDifferentBookingIdSameDatesSameRoom = service.checkIfBookingPossible(differentBookingIdSameDatesSameRoom);
        boolean feedbackDifferentBookingIdDifferentDates = service.checkIfBookingPossible(differentBookingIdDifferentDates);

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

    @Test
    void findBookingByDates() {
        Booking booking2 = new Booking(2L, LocalDate.now().plusDays(5), LocalDate.now().plusDays(10), 456, 2, 5000, customer, room);
        List<Booking> bookings = Arrays.asList(booking, booking2);

        when(bookingRepo.findAllByStartDateIsBeforeAndEndDateIsAfter(LocalDate.now().plusDays(10), LocalDate.now().minusDays(1))).thenReturn(bookings);

        List<DetailedBookingDto> resultWithBothBookingsFound = service.findBookingByDates(LocalDate.now().minusDays(1), LocalDate.now().plusDays(10));
        assertNotNull(resultWithBothBookingsFound);
        assertEquals(2, resultWithBothBookingsFound.size());

        when(bookingRepo.findAllByStartDateIsBeforeAndEndDateIsAfter(LocalDate.now().plusDays(3), LocalDate.now().minusDays(1))).thenReturn(Collections.singletonList(booking));

        List<DetailedBookingDto> resultWithOneBookingFound = service.findBookingByDates(LocalDate.now().minusDays(1), LocalDate.now().plusDays(3));
        assertNotNull(resultWithOneBookingFound);
        assertEquals(1, resultWithOneBookingFound.size());

        when(bookingRepo.findAllByStartDateIsBeforeAndEndDateIsAfter(LocalDate.now().plusDays(15), LocalDate.now().plusDays(11))).thenReturn(Collections.emptyList());

        List<DetailedBookingDto> resultWithNoBookingsFound = service.findBookingByDates(LocalDate.now().plusDays(11), LocalDate.now().plusDays(15));
        assertNotNull(resultWithNoBookingsFound);
        assertEquals(0, resultWithNoBookingsFound.size());

        List<DetailedBookingDto> resultWithInvalidDateRanges = service.findBookingByDates(LocalDate.now().plusDays(10), LocalDate.now().minusDays(1));
        assertEquals(0, resultWithInvalidDateRanges.size());
    }
}