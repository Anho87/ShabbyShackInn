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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoomServiceImplTests {


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

    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(1);
    int bookingNumber = 123;
    int extraBedsWanted = 1;
    int price = 1000;
    int totalPrice = 99999;
    Long roomId = 1L;
    int roomNumber = 1;
    RoomType roomType = RoomType.DOUBLE;
    int beds = 2;
    int possibleExtraBeds = 1;
    List<Booking> bookings = new ArrayList<>();
    Long bookingId = 1L;

    Room roomWith3Beds = new Room(roomId, roomType, roomNumber, beds, price, possibleExtraBeds);

    Room roomWith2Beds = new Room(2L, roomType, 2, beds, 100, 0);
    Room roomWith4Beds = new Room(2L, roomType, 2, beds, 100, 2);

    Customer customer = new Customer(customerId, firstName, lastName, phone, email, bookings);

    Booking booking = new Booking(bookingId, startDate, endDate, bookingNumber, extraBedsWanted, totalPrice, customer, roomWith3Beds);

    MiniRoomDto miniRoomDto = new MiniRoomDto(roomId, roomType, roomNumber);

    MiniCustomerDto miniCustomerDto = new MiniCustomerDto(customerId, firstName, lastName, email);

    MiniBookingDto miniBookingDto = new MiniBookingDto(bookingId, startDate, endDate, miniRoomDto, miniCustomerDto);


    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice).build();

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(roomId).roomType(roomType).roomNumber(roomNumber).price(price)
            .beds(beds).possibleExtraBeds(possibleExtraBeds).build();

    @Test
    void roomToDetailedRoomDTO() {
        DetailedRoomDto actual = service.roomToDetailedRoomDTO(roomWith3Beds);

        assertEquals(actual.getId(), roomWith3Beds.getId());
        assertEquals(actual.getRoomType().roomType, roomWith3Beds.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), roomWith3Beds.getRoomNumber());
        assertEquals(actual.getBeds(), roomWith3Beds.getBeds());
        assertEquals(actual.getPrice(), roomWith3Beds.getPrice());
        assertEquals(actual.getPossibleExtraBeds(), roomWith3Beds.getPossibleExtraBeds());
    }

    @Test
    void getAllRooms() {
        when(roomRepo.findAll()).thenReturn(Arrays.asList(roomWith3Beds));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        List<DetailedRoomDto> allRoom = service2.getAllRooms();

        assertEquals(1, allRoom.size());
        assertEquals(1L, allRoom.get(0).getId());
    }

    @Test
    void detailedRoomToRoom() {
        Room actual = service.detailedRoomToRoom(detailedRoomDto);

        assertEquals(actual.getId(), detailedRoomDto.getId());
        assertEquals(actual.getRoomType().roomType, detailedRoomDto.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), detailedRoomDto.getRoomNumber());
        assertEquals(actual.getBeds(), detailedRoomDto.getBeds());
        assertEquals(actual.getPrice(), roomWith3Beds.getPrice());
        assertEquals(actual.getPossibleExtraBeds(), detailedRoomDto.getPossibleExtraBeds());
    }

    @Test
    void roomToMiniRoomDto() {
        MiniRoomDto actual = service.roomToMiniRoomDto(roomWith3Beds);

        assertEquals(actual.getId(), roomWith3Beds.getId());
        assertEquals(actual.getRoomType().roomType, roomWith3Beds.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), roomWith3Beds.getRoomNumber());
    }

    @Test
    void findDetailedRoomById() {
        when(roomRepo.findById(roomWith3Beds.getId())).thenReturn(Optional.of(roomWith3Beds));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        DetailedRoomDto actual = service2.findDetailedRoomById(roomWith3Beds.getId());
        assertEquals(actual.getId(), roomWith3Beds.getId());
        assertEquals(actual.getRoomType().roomType, roomWith3Beds.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), roomWith3Beds.getRoomNumber());
        assertEquals(actual.getBeds(), roomWith3Beds.getBeds());
        assertEquals(actual.getPrice(), roomWith3Beds.getPrice());
        assertEquals(actual.getPossibleExtraBeds(), roomWith3Beds.getPossibleExtraBeds());
    }

    @Test
    void findMiniRoomById() {
        when(roomRepo.findById(roomWith3Beds.getId())).thenReturn(Optional.of(roomWith3Beds));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        MiniRoomDto actual = service2.findMiniRoomById(roomWith3Beds.getId());
        assertEquals(actual.getId(), roomWith3Beds.getId());
        assertEquals(actual.getRoomType().roomType, roomWith3Beds.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), roomWith3Beds.getRoomNumber());
    }

    @Test
    void findMiniRoomByRoomNumber() {
        when(roomRepo.findRoomByRoomNumber(roomWith3Beds.getRoomNumber())).thenReturn(roomWith3Beds);
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        MiniRoomDto actual = service2.findMiniRoomByRoomNumber(roomWith3Beds.getRoomNumber());
        assertEquals(actual.getId(), roomWith3Beds.getId());
        assertEquals(actual.getRoomType().roomType, roomWith3Beds.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), roomWith3Beds.getRoomNumber());
    }


}
