package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.Booking;
import com.example.shabbyshackinn.models.Customer;
import com.example.shabbyshackinn.models.Room;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BookingService;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    
    final private BookingRepo bookingRepo;
    final private CustomerRepo customerRepo;
    final private RoomRepo roomRepo;

    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo,RoomRepo roomRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
    }

    @Override
    public MiniBookingDto bookingToMiniBookingDto(Booking booking) {
        MiniCustomerDto miniCustomerDto = (booking.getCustomer() != null) ?
                new MiniCustomerDto(booking.getCustomer().getId(), booking.getCustomer().getFirstName(), booking.getCustomer().getLastName(),booking.getCustomer().getEMail()) :
                null;
        
        return MiniBookingDto.builder().id(booking.getId()).startDate(booking.getStartDate()).endDate(booking.getEndDate())
                .miniRoomDto(new MiniRoomDto(booking.getRoom().getId(), booking.getRoom().getRoomType(), booking.getRoom().getRoomNumber()))
                .miniCustomerDto(miniCustomerDto)
                .build();
    }

    @Override
    public DetailedBookingDto bookingToDetailedBookingDto(Booking booking) {
        
        MiniCustomerDto miniCustomerDto = (booking.getCustomer() != null) ?
                new MiniCustomerDto(booking.getCustomer().getId(), booking.getCustomer().getFirstName(), booking.getCustomer().getLastName(),booking.getCustomer().getEMail()) :
                null;

        return DetailedBookingDto.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .bookingNumber(booking.getBookingNumber())
                .extraBedsWanted(booking.getExtraBedsWanted())
                .miniCustomerDto(miniCustomerDto)
                .miniRoomDto(new MiniRoomDto(booking.getRoom().getId(), booking.getRoom().getRoomType(), booking.getRoom().getRoomNumber()))
                .build();
    }
    

    @Override
    public Booking detailedBookingDtoToBooking(DetailedBookingDto detailedBookingDto, Customer customer, Room room) {
        return Booking.builder().id(detailedBookingDto.getId()).startDate(detailedBookingDto.getStartDate())
                .endDate(detailedBookingDto.getEndDate()).bookingNumber(detailedBookingDto.getBookingNumber())
                .extraBedsWanted(detailedBookingDto.getExtraBedsWanted()).customer(customer).room(room).build();
    }

    @Override
    public List<DetailedBookingDto> getAllBookings() {
        return bookingRepo.findAll().stream().map(booking -> bookingToDetailedBookingDto(booking)).toList();
    }
    @Override
    public List<MiniBookingDto> getAllMiniBookings(){
        return bookingRepo.findAll().stream().map(booking -> bookingToMiniBookingDto(booking)).toList();
    }

    @Override
    public List<MiniBookingDto> getAllCurrentAndFutureMiniBookings(){
        LocalDate todaysDate = LocalDate.now();
        return bookingRepo.findAll().stream().filter(booking -> booking.getEndDate().isAfter(todaysDate)).map(booking -> bookingToMiniBookingDto(booking)).toList();
    }

    @Override
    public String addBooking(DetailedBookingDto detailedBookingDto) {
        if (checkIfBookingPossible(detailedBookingDto) && detailedBookingDto.getStartDate().isBefore(detailedBookingDto.getEndDate()) ){
            Customer customer = customerRepo.findById(detailedBookingDto.getMiniCustomerDto().getId()).get();
            Room room = roomRepo.findById(detailedBookingDto.getMiniRoomDto().getId()).get();
            bookingRepo.save(detailedBookingDtoToBooking(detailedBookingDto,customer,room));
            return "Booking added";
        }
        return "Booking not added";
    }

    @Override
    public String updateBooking(DetailedBookingDto detailedBookingDto) {
        if (checkIfBookingPossible(detailedBookingDto) && detailedBookingDto.getStartDate().isBefore(detailedBookingDto.getEndDate())){
            Customer customer = bookingRepo.findById(detailedBookingDto.getId()).get().getCustomer();
            Room room = roomRepo.findById(detailedBookingDto.getMiniRoomDto().getId()).get();
            bookingRepo.save(detailedBookingDtoToBooking(detailedBookingDto,customer,room));
            return "Booking updated";
        }
        return "Booking not updated";
    }

    @Override
    public String deleteBooking(Long id) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if(booking == null){
            return "Booking not found";
        }
        bookingRepo.deleteById(id);
        return "Booking deleted";
    }

    @Override
    public boolean checkIfBookingPossible(DetailedBookingDto booking) {
        Long roomId = booking.getMiniRoomDto().getId();
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        Long currentBookingId = booking.getId();

        List<Booking> overlappingBookings = bookingRepo.findAll()
                .stream()
                .filter(b -> !b.getId().equals(currentBookingId))
                .filter(b -> b.getRoom().getId().equals(roomId))
                .filter(b -> b.getStartDate().isBefore(endDate) && b.getEndDate().isAfter(startDate))
                .toList();

        return overlappingBookings.isEmpty();
    }
}
