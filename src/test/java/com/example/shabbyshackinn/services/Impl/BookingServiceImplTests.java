package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.models.*;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BlacklistService;
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
    private BookingServiceImpl service = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);

    Long customerId = 1L;
    String firstName = "John";
    String lastName = "Doe";
    String phone = "123456789";
    String email = "john.doe@example.com";

    LocalDate startDate = LocalDate.now().plusDays(1);
    LocalDate endDate = startDate.plusDays(2);
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

    Customer customer = new Customer(customerId, firstName, lastName, phone, email,bookings);

    Booking booking = new Booking(bookingId, startDate, endDate, bookingNumber, extraBedsWanted, totalPrice, customer, room);

    BlacklistResponse blacklistResponseNotBlacklisted = new BlacklistResponse("OK", true);

    BlacklistResponse blacklistResponseBlacklisted = new BlacklistResponse("Blacklisted", false);
    
    MiniRoomDto miniRoomDto = new MiniRoomDto(roomId, roomType, roomNumber);

    MiniCustomerDto miniCustomerDto = new MiniCustomerDto(customerId, firstName, lastName, email);

    MiniBookingDto miniBookingDto = new MiniBookingDto(bookingId, startDate, endDate, miniRoomDto, miniCustomerDto);

    DetailedCustomerDto detailedCustomerDto = DetailedCustomerDto.builder().id(customerId).firstName(firstName)
            .lastName(lastName).eMail(email).phone(phone).bookings(customer.getBookings().stream()
                    .map(booking -> service.bookingToMiniBookingDto(booking)).toList()).build();

    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice) .build();

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(roomId).roomType(roomType).roomNumber(roomNumber)
            .beds(beds).possibleExtraBeds(possibleExtraBeds).build();

    @Test
    void getMiniAllBookings(){
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);
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
    void detailedBookingDtoToBooking(){
        Booking actual = service.detailedBookingDtoToBooking(detailedBookingDto,customer,room);
        
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
    void getAllCurrentAndFutureMiniBookings(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(bookingRepo.findAllByEndDateAfter(yesterday)).thenReturn(Collections.singletonList(booking));
        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);
        List<MiniBookingDto> allCurrentAndFutureMiniBookings = service2.getAllCurrentAndFutureMiniBookings();
        assertEquals(1, allCurrentAndFutureMiniBookings.size());
    }
    
    @Test
    void addBooking(){
        when(bookingRepo.save(booking)).thenReturn(booking);
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(blacklistService.checkIfEmailIsBlacklisted(customer.getEMail())).thenReturn(blacklistResponseNotBlacklisted);
        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);
        String feedback = service2.addBooking(detailedBookingDto);
        System.out.println("Feedback: " + feedback);
        assertTrue(feedback.equalsIgnoreCase("Booking added"));
    }
    
    @Test
    void updateBooking(){
        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);
        String feedBack = service2.updateBooking(detailedBookingDto);
        System.out.println("feedback" + feedBack);
        assertTrue(feedBack.equalsIgnoreCase("Booking updated"));
    }
    
    @Test
    void deleteBooking(){
        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(blacklistService.checkIfEmailIsBlacklisted(customer.getEMail())).thenReturn(blacklistResponseBlacklisted);
        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);
        service2.addBooking(detailedBookingDto);
        String feedback = service2.deleteBooking(bookingId);
        System.out.println("Feedback: " + feedback);
        assertTrue(feedback.equalsIgnoreCase("Booking deleted"));
    }
    
    @Test
    void checkIfBookingPossible(){
        List<Booking> allBookings = Arrays.asList(booking);
//
//        when(bookingRepo.findAll()).thenReturn(allBookings);
        when(bookingRepo.findAllByIdIsNotAndRoomIdAndStartDateIsBeforeAndEndDateIsAfter(
                booking.getId(),
                booking.getRoom().getId(),
                booking.getEndDate(),
                booking.getStartDate())).thenReturn(allBookings);
//        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
//        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
//        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
//        when(blacklistService.checkIfEmailIsBlacklisted(customer.getEMail())).thenReturn(blacklistResponseNotBlacklisted);
        
       
        DetailedBookingDto DifferentBookingIdSameDatesSameRoom = DetailedBookingDto.builder().id(3L)
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
                .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice).build();

        DetailedBookingDto DifferentBookingIdDifferentDates = DetailedBookingDto.builder().id(3L)
                .startDate(startDate.plusDays(5)).endDate(endDate.plusDays(6)).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
                .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice).build();

        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService,bookingRepo, customerRepo, roomRepo, discountService);

        boolean feedbackSameBooking = service2.checkIfBookingPossible(detailedBookingDto);
        boolean feedbackDifferentBookingIdSameDatesSameRoom = service2.checkIfBookingPossible(DifferentBookingIdSameDatesSameRoom);
        boolean feedbackDifferentBookingIdDifferentDates = service2.checkIfBookingPossible(DifferentBookingIdDifferentDates);

        assertTrue(feedbackSameBooking);
        assertFalse(feedbackDifferentBookingIdSameDatesSameRoom);
        assertTrue(feedbackDifferentBookingIdDifferentDates);
    }
    
    @Test
    void findDetailedBookingById(){
        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
        BookingServiceImpl service2 = new BookingServiceImpl(blacklistService, bookingRepo, customerRepo, roomRepo, discountService);
        DetailedBookingDto actual = service2.findDetailedBookingById(booking.getId());
        
        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getBookingNumber(), booking.getBookingNumber());
        assertEquals(actual.getExtraBedsWanted(),booking.getExtraBedsWanted());
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