package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private BookingRepo bookingRepo;
    
    @Mock
    private RoomRepo roomRepo;

    @InjectMocks
    private BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);

    Long customerId = 1L;
    String firstName = "John";
    String lastName = "Doe";
    String phone = "123456789";
    String email = "john.doe@example.com";

    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 3);
    int bookingNumber = 123;
    int extraBedsWanted = 1;
    Long roomId = 1L;
    int roomNumber = 1;
    RoomType roomType = RoomType.DOUBLE;
    int beds = 2;
    int possibleExtraBeds = 1;
    List<Booking> bookings = new ArrayList<>();
    Long bookingId = 1L;

    Room room = new Room(roomId, roomType, roomNumber, beds, possibleExtraBeds);

    Customer customer = new Customer(customerId, firstName, lastName, phone, email,bookings);

    Booking booking = new Booking(bookingId, customer, startDate, endDate, bookingNumber, extraBedsWanted, room);

    MiniRoomDto miniRoomDto = new MiniRoomDto(roomId, roomType, roomNumber);

    MiniCustomerDto miniCustomerDto = new MiniCustomerDto(customerId, firstName, lastName, email);

    MiniBookingDto miniBookingDto = new MiniBookingDto(bookingId, startDate, endDate, miniRoomDto, miniCustomerDto);

    DetailedCustomerDto detailedCustomerDto = DetailedCustomerDto.builder().id(customerId).firstName(firstName)
            .lastName(lastName).eMail(email).phone(phone).bookings(customer.getBookings().stream()
                    .map(booking -> service.bookingToMiniBookingDto(booking)).toList()).build();

    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).build();

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(roomId).roomType(roomType).roomNumber(roomNumber)
            .beds(beds).possibleExtraBeds(possibleExtraBeds).build();

    @Test
    void getMiniAllBookings(){
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl service2 = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);
        List<MiniBookingDto> allCustomers = service2.getAllMiniBookings();

        assertEquals(1, allCustomers.size());
    }

    @Test
    void bookingToMiniBookingDto(){
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
    void bookingToDetailedBookingDto(){
        DetailedBookingDto actual = service.bookingToDetailedBookingDto(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getBookingNumber(), booking.getBookingNumber());
        assertEquals(actual.getExtraBedsWanted(), booking.getExtraBedsWanted());
        
        assertEquals(actual.getMiniCustomerDto().getId(), booking.getCustomer().getId());
        assertEquals(actual.getMiniCustomerDto().getFirstName(), booking.getCustomer().getFirstName());
        assertEquals(actual.getMiniCustomerDto().getLastName(), booking.getCustomer().getLastName());
        assertEquals(actual.getMiniCustomerDto().getEMail(), booking.getCustomer().getEMail());
        
        assertEquals(actual.getMiniRoomDto().getId(), booking.getRoom().getId());
        assertEquals(actual.getMiniRoomDto().getRoomType().roomType, booking.getRoom().getRoomType().roomType);
        assertEquals(actual.getMiniRoomDto().getRoomNumber(), booking.getRoom().getRoomNumber());
    }
    
    @Test
    void detailedBookingDtoToBooking(){
        Booking actual = service.detailedBookingDtoToBooking(detailedBookingDto,customer,room);
        
        assertEquals(actual.getId(), detailedBookingDto.getId());
        assertEquals(actual.getStartDate(), detailedBookingDto.getStartDate());
        assertEquals(actual.getEndDate(), detailedBookingDto.getEndDate());
        assertEquals(actual.getBookingNumber(), detailedBookingDto.getBookingNumber());
        assertEquals(actual.getExtraBedsWanted(), detailedBookingDto.getExtraBedsWanted());
        
        assertEquals(actual.getCustomer().getId(), detailedBookingDto.getMiniCustomerDto().getId());
        assertEquals(actual.getCustomer().getFirstName(), detailedBookingDto.getMiniCustomerDto().getFirstName());
        assertEquals(actual.getCustomer().getLastName(), detailedBookingDto.getMiniCustomerDto().getLastName());
        assertEquals(actual.getCustomer().getEMail(), detailedBookingDto.getMiniCustomerDto().getEMail());
        
        assertEquals(actual.getRoom().getId(), detailedBookingDto.getMiniRoomDto().getId());
        assertEquals(actual.getRoom().getRoomType().roomType, detailedBookingDto.getMiniRoomDto().getRoomType().roomType);
        assertEquals(actual.getRoom().getRoomNumber(), detailedBookingDto.getMiniRoomDto().getRoomNumber());
    }
}