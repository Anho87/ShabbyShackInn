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

    LocalDate startDate = LocalDate.of(2020, 1, 1);
    LocalDate endDate = LocalDate.of(2020, 1, 3);
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

    Room room = new Room(roomId, roomType, roomNumber, beds, price, possibleExtraBeds);

    Customer customer = new Customer(customerId, firstName, lastName, phone, email,bookings);

    Booking booking = new Booking(bookingId, startDate, endDate, bookingNumber, extraBedsWanted, totalPrice, customer, room);

    MiniRoomDto miniRoomDto = new MiniRoomDto(roomId, roomType, roomNumber);

    MiniCustomerDto miniCustomerDto = new MiniCustomerDto(customerId, firstName, lastName, email);

    MiniBookingDto miniBookingDto = new MiniBookingDto(bookingId, startDate, endDate, miniRoomDto, miniCustomerDto);
    

    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice).build();

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(roomId).roomType(roomType).roomNumber(roomNumber).price(price)
            .beds(beds).possibleExtraBeds(possibleExtraBeds).build();
    
    
    @Test
    void roomToDetailedRoomDTO(){
        DetailedRoomDto actual = service.roomToDetailedRoomDTO(room);

        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().roomType, room.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), room.getRoomNumber());
        assertEquals(actual.getBeds(), room.getBeds());
        assertEquals(actual.getPrice(), room.getPrice());
        assertEquals(actual.getPossibleExtraBeds(), room.getPossibleExtraBeds());
    }
    
    @Test
    void getAllRooms(){
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        List<DetailedRoomDto> allRoom = service2.getAllRooms();

        assertEquals(1, allRoom.size());
    }
    
    @Test
    void detailedRoomToRoom(){
        Room actual = service.detailedRoomToRoom(detailedRoomDto);
        
        assertEquals(actual.getId(), detailedRoomDto.getId());
        assertEquals(actual.getRoomType().roomType, detailedRoomDto.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), detailedRoomDto.getRoomNumber());
        assertEquals(actual.getBeds(), detailedRoomDto.getBeds());
        assertEquals(actual.getPrice(), room.getPrice());
        assertEquals(actual.getPossibleExtraBeds(), detailedRoomDto.getPossibleExtraBeds());
    }
    
    @Test
    void roomToMiniRoomDto(){
        MiniRoomDto actual = service.roomToMiniRoomDto(room);
        
        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().roomType, room.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), room.getRoomNumber());
    }
    
    @Test
    void findDetailedRoomById(){
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        DetailedRoomDto actual = service2.findDetailedRoomById(room.getId());
        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().roomType, room.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), room.getRoomNumber());
        assertEquals(actual.getBeds(), room.getBeds());
        assertEquals(actual.getPrice(), room.getPrice());
        assertEquals(actual.getPossibleExtraBeds(), room.getPossibleExtraBeds());
    }
    
    @Test
    void findMiniRoomById(){
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        MiniRoomDto actual = service2.findMiniRoomById(room.getId());
        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().roomType, room.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), room.getRoomNumber());
    }

    @Test
    void findAvailableRoomsForThreePeople() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        int amountOfPersons = 3;
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        List<DetailedRoomDto> detailedRoomDtoList = service2.findAvailableRooms(startDate, endDate, amountOfPersons);

        assertEquals(1, detailedRoomDtoList.size());
    }

    @Test
    void findAvailableRoomsForFourPeople() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        int amountOfPersons = 4;
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        List<DetailedRoomDto> detailedRoomDtoList = service2.findAvailableRooms(startDate, endDate, amountOfPersons);

        assertEquals(0, detailedRoomDtoList.size());
    }

    @Test
    void findMiniRoomByRoomNumber() {
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        RoomServiceImpl service2 = new RoomServiceImpl(roomRepo, bookingRepo);
        MiniRoomDto actual = service2.findMiniRoomByRoomNumber(room.getRoomNumber());
        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getRoomType().roomType, room.getRoomType().roomType);
        assertEquals(actual.getRoomNumber(), room.getRoomNumber());
    }
    
    
}
