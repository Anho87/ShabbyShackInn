package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedCustomerDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.services.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final BookingRepo bookingRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo,BookingRepo bookingRepo) {
        this.customerRepo = customerRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public List<DetailedCustomerDto> getAllCustomers() {
        return customerRepo.findAll().stream().map(c -> customerToDetailedCustomerDTO(c)).toList();
    }
    @Override
    public List<MiniCustomerDto> getallMiniCustomers(){
        return customerRepo.findAll().stream().map(c -> customerToMiniCustomerDto(c)).toList();
    }

    @Override
    public DetailedCustomerDto customerToDetailedCustomerDTO(Customer customer) {
        return DetailedCustomerDto.builder().id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).phone(customer.getPhone())
                .eMail(customer.getEMail())
                .bookings(customer.getBookings().stream()
                        .map(booking -> bookingToMiniBookingDto(booking)).toList()).build();
    }
    
    public MiniBookingDto bookingToMiniBookingDto(Booking booking){
        return MiniBookingDto.builder().id(booking.getId()).startDate(booking.getStartDate()).endDate(booking.getEndDate())
                .miniRoomDto(new MiniRoomDto(booking.getRoom().getId(),booking.getRoom().getRoomType(),booking.getRoom().getRoomNumber()))
                .miniCustomerDto(new MiniCustomerDto(booking.getCustomer().getId(),booking.getCustomer().getFirstName(),booking.getCustomer().getLastName(),booking.getCustomer().getEMail())).build();
    }
    
    public Customer findCustomerById(Long id){
        return customerRepo.findById(id).orElse(null);
    }
    
    public MiniCustomerDto findMiniCustomerById(Long id){
        return customerToMiniCustomerDto(customerRepo.findById(id).get());
    }

    @Override
    public String addCustomer(DetailedCustomerDto customer) {
        customerRepo.save(detailedCustomerToCustomer(customer));
        return "Customer " + customer.getFirstName() + " is saved";
    }
    
    @Override
    public String updateCustomer(DetailedCustomerDto customer){
        customerRepo.save(detailedCustomerToCustomer(customer));
        return "Customer " + customer.getFirstName() + " is updated";
    }

    @Override
    public Customer detailedCustomerToCustomer(DetailedCustomerDto c) {
        return Customer.builder().id(c.getId()).firstName(c.getFirstName()).lastName(c.getLastName())
                .phone(c.getPhone()).eMail(c.getEMail()).build();
                
    }

    @Override
    public MiniCustomerDto customerToMiniCustomerDto(Customer customer) {
        return MiniCustomerDto.builder().id(customer.getId()).firstName(customer.getFirstName())
                .lastName(customer.getLastName()).eMail(customer.getEMail()).build();
    }
    
    @Override
    public String deleteCustomer(Long id){
        LocalDate dateOfToday = LocalDate.now();
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer == null) {
            return "Customer not found";
        }
        
        List<Booking> bookings = customer.getBookings();
        boolean hasNoActiveBookings = bookings.stream()
                .allMatch(booking -> dateOfToday.isAfter(booking.getEndDate()));
        if (!hasNoActiveBookings) {
            return "Customer has ongoing bookings";
        }
        for (Booking booking : bookings) {
            booking.setCustomer(null);
            bookingRepo.save(booking);
        }
        customerRepo.deleteById(id);
        return "Customer deleted";
    }
}


