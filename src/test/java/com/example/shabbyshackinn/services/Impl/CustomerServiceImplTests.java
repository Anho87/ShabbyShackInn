package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.*;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.models.RoomType;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerServiceImplTests {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private BookingRepo bookingRepo;

    @InjectMocks
    private CustomerServiceImpl service = new CustomerServiceImpl(customerRepo, bookingRepo);

    Long customerId = 1L;
    String firstName = "John";
    String lastName = "Doe";
    String phone = "123456789";
    String email = "john.doe@example.com";

    LocalDate startDate = LocalDate.now();
    LocalDate endDate = startDate.plusDays(1);
    int bookingNumber = 123;
    int extraBedsWanted = 1;
    int totalPrice = 9999999;
    Long roomId = 1L;
    int roomNumber = 1;
    RoomType roomType = RoomType.DOUBLE;
    int beds = 2;
    int price = 1000;
    int possibleExtraBeds = 1;
    List<Booking> bookings = new ArrayList<>();
    Long bookingId = 1L;

    Room room = new Room(roomId, roomType, roomNumber, beds, price, possibleExtraBeds);

    Customer customer = new Customer(customerId, firstName, lastName, phone, email, bookings);

    Booking booking = new Booking(bookingId, startDate, endDate, bookingNumber, extraBedsWanted, totalPrice, customer, room);

    MiniRoomDto miniRoomDto = new MiniRoomDto(roomId, roomType, roomNumber);

    MiniCustomerDto miniCustomerDto = new MiniCustomerDto(customerId, firstName, lastName, email);

    MiniBookingDto miniBookingDto = new MiniBookingDto(bookingId, startDate, endDate, miniRoomDto, miniCustomerDto);

    DetailedCustomerDto detailedCustomerDto = DetailedCustomerDto.builder().id(customerId).firstName(firstName)
            .lastName(lastName).eMail(email).phone(phone).build();

    DetailedBookingDto detailedBookingDto = DetailedBookingDto.builder().id(bookingId)
            .startDate(startDate).endDate(endDate).bookingNumber(bookingNumber).extraBedsWanted(extraBedsWanted)
            .miniCustomerDto(miniCustomerDto).miniRoomDto(miniRoomDto).totalPrice(totalPrice).build();

    DetailedRoomDto detailedRoomDto = DetailedRoomDto.builder().id(roomId).roomType(roomType).roomNumber(roomNumber)
            .beds(beds).possibleExtraBeds(possibleExtraBeds).build();

    @Test
    void getMiniAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        List<MiniCustomerDto> allCustomers = service2.getallMiniCustomers();

        assertEquals(1, allCustomers.size());
    }

    @Test
    void getAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        List<DetailedCustomerDto> allCustomers = service2.getAllCustomers();

        assertEquals(1, allCustomers.size());
        assertEquals(allCustomers.get(0).getFirstName(),customer.getFirstName());
    }

    @Test
    void customerToMiniCustomerDto() {
        MiniCustomerDto actual = service.customerToMiniCustomerDto(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
        assertEquals(actual.getLastName(), customer.getLastName());
        assertEquals(actual.getEMail(), customer.getEMail());
    }

    @Test
    void customerToDetailedCustomerDto() {
        DetailedCustomerDto actual = service.customerToDetailedCustomerDTO(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
        assertEquals(actual.getLastName(), customer.getLastName());
        assertEquals(actual.getEMail(), customer.getEMail());
        assertEquals(actual.getPhone(), customer.getPhone());
    }

    @Test
    void detailedCustomerToCustomer() {
        Customer actual = service.detailedCustomerToCustomer(detailedCustomerDto);

        assertEquals(actual.getId(), detailedCustomerDto.getId());
        assertEquals(actual.getFirstName(), detailedCustomerDto.getFirstName());
        assertEquals(actual.getLastName(), detailedCustomerDto.getLastName());
        assertEquals(actual.getEMail(), detailedCustomerDto.getEMail());
        assertEquals(actual.getPhone(), detailedCustomerDto.getPhone());
    }

    @Test
    void addCustomer() {
        when(customerRepo.save(customer)).thenReturn(customer);
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);

        String feedBack = service2.addCustomer(detailedCustomerDto);
        System.out.println("feedback" + feedBack);
        assertTrue(feedBack.equalsIgnoreCase("Customer " + customer.getFirstName() + " is saved"));
    }

    @Test
    void removeCustomer() {
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        service2.addCustomer(detailedCustomerDto);
        String feedBack = service2.deleteCustomer(customerId);
        System.out.println("feedback " + feedBack);
        assertTrue(feedBack.equalsIgnoreCase("Customer deleted"));
    }

    @Test
    void updateCustomer() {
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        String feedBack = service2.updateCustomer(detailedCustomerDto);
        System.out.println("feedback" + feedBack);
        assertTrue(feedBack.equalsIgnoreCase("Customer " + customer.getFirstName() + " is updated"));

    }

    @Test
    void findMiniCustomerById() {
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        MiniCustomerDto actual = service2.findMiniCustomerById(customer.getId());

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
        assertEquals(actual.getLastName(), customer.getLastName());
        assertEquals(actual.getEMail(), customer.getEMail());
    }

    @Test
    void findDetailedCustomerDtoById() {
        when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerServiceImpl service2 = new CustomerServiceImpl(customerRepo, bookingRepo);
        DetailedCustomerDto actual = service2.findDetailedCustomerDtoById(customer.getId());

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
        assertEquals(actual.getLastName(), customer.getLastName());
        assertEquals(actual.getEMail(), customer.getEMail());
        assertEquals(actual.getPhone(), customer.getPhone());
    }

    @Test
    void customerHasNoActiveBookings() {
        Boolean actual = service.customerHasActiveBookings(customer);
        assertTrue(actual);
    }

    @Test
    void customerHasActiveBookings() {
        customer.addBooking(booking);
        Boolean actual = service.customerHasActiveBookings(customer);
        assertFalse(actual);
    }
}
