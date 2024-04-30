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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomServiceImplTest {
    

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;

    @InjectMocks
    private RoomServiceImpl service = new RoomServiceImpl(roomRepo, bookingRepo);
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
    

    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).build();

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(roomId).roomType(roomType).roomNumber(roomNumber)
            .beds(beds).possibleExtraBeds(possibleExtraBeds).build();
    
    
    @Test
    void roomToDetailedRoomDTO(){
        DetailedRoomDto actual = service.roomToDetailedRoomDTO(room);

        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().roomType, room.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), room.getRoomNumber());
        assertEquals(actual.getBeds(), room.getBeds());
        assertEquals(actual.getPossibleExtraBeds(), room.getPossibleExtraBeds());
    }
}
