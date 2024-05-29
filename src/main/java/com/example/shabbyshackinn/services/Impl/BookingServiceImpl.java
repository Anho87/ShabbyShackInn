package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.email.EmailService;
import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.MiniBookingDto;
import com.example.shabbyshackinn.dtos.MiniCustomerDto;
import com.example.shabbyshackinn.dtos.MiniRoomDto;
import com.example.shabbyshackinn.models.*;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.CustomerRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.BlacklistService;
import com.example.shabbyshackinn.services.BookingService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    final private BookingRepo bookingRepo;
    final private CustomerRepo customerRepo;
    final private RoomRepo roomRepo;
    final private BlacklistService blacklistService;
    final private DiscountServiceImpl discountService;
    final private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;


    public BookingServiceImpl(BlacklistService blacklistService, BookingRepo bookingRepo, CustomerRepo customerRepo, RoomRepo roomRepo, DiscountServiceImpl discountService, JavaMailSender javaMailSender, TemplateEngine templateEngine, EmailService emailService) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
        this.blacklistService = blacklistService;
        this.discountService = discountService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.emailService = emailService;
    }

    @Override
    public MiniBookingDto bookingToMiniBookingDto(Booking booking) {
        MiniCustomerDto miniCustomerDto = (booking.getCustomer() != null) ?
                new MiniCustomerDto(booking.getCustomer().getId(), booking.getCustomer().getFirstName(), booking.getCustomer().getLastName(), booking.getCustomer().getEMail()) :
                null;

        return MiniBookingDto.builder().id(booking.getId()).startDate(booking.getStartDate()).endDate(booking.getEndDate())
                .miniRoomDto(new MiniRoomDto(booking.getRoom().getId(), booking.getRoom().getRoomType(), booking.getRoom().getRoomNumber()))
                .miniCustomerDto(miniCustomerDto)
                .build();
    }

    @Override
    public DetailedBookingDto bookingToDetailedBookingDto(Booking booking) {

        MiniCustomerDto miniCustomerDto = (booking.getCustomer() != null) ?
                new MiniCustomerDto(booking.getCustomer().getId(), booking.getCustomer().getFirstName(), booking.getCustomer().getLastName(), booking.getCustomer().getEMail()) :
                null;

        return DetailedBookingDto.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .bookingNumber(booking.getBookingNumber())
                .extraBedsWanted(booking.getExtraBedsWanted())
                .totalPrice(booking.getTotalPrice())
                .miniCustomerDto(miniCustomerDto)
                .miniRoomDto(new MiniRoomDto(booking.getRoom().getId(), booking.getRoom().getRoomType(), booking.getRoom().getRoomNumber()))
                .build();
    }

    @Override
    public DetailedBookingDto findDetailedBookingById(Long id) {
        return bookingToDetailedBookingDto(bookingRepo.findById(id).get());
    }


    @Override
    public Booking detailedBookingDtoToBooking(DetailedBookingDto detailedBookingDto, Customer customer, Room room) {
        return Booking.builder().id(detailedBookingDto.getId()).startDate(detailedBookingDto.getStartDate())
                .endDate(detailedBookingDto.getEndDate()).bookingNumber(detailedBookingDto.getBookingNumber())
                .extraBedsWanted(detailedBookingDto.getExtraBedsWanted()).customer(customer).room(room).
                totalPrice(detailedBookingDto.getTotalPrice()).build();
    }

    @Override
    public List<MiniBookingDto> getAllMiniBookings() {
        return bookingRepo.findAll().stream().map(booking -> bookingToMiniBookingDto(booking)).toList();
    }

    @Override
    public List<MiniBookingDto> getAllCurrentAndFutureMiniBookings() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return bookingRepo.findAllByEndDateAfter(yesterday).stream().map(this::bookingToMiniBookingDto).toList();
    }

    @Override
    public String addBooking(DetailedBookingDto detailedBookingDto) {
        BlacklistResponse blacklistResponse = blacklistService.checkIfEmailIsBlacklisted(detailedBookingDto.getMiniCustomerDto().getEMail());
        System.out.println("isCustomerOkInBlacklist " + detailedBookingDto.getMiniCustomerDto().getEMail() + blacklistResponse.isOk());
        if (!blacklistResponse.isOk()) {
            return "Booking not added, " + detailedBookingDto.getMiniCustomerDto().getEMail() + " is blacklisted!";
        }

        if (checkIfBookingPossible(detailedBookingDto) && detailedBookingDto.getStartDate().isBefore(detailedBookingDto.getEndDate())) {
            detailedBookingDto.setTotalPrice(discountService.calculateDiscount(detailedBookingDto.getMiniRoomDto().getId()
                    , detailedBookingDto.getMiniCustomerDto().getId(), detailedBookingDto));
            Customer customer = customerRepo.findById(detailedBookingDto.getMiniCustomerDto().getId()).get();
            Room room = roomRepo.findById(detailedBookingDto.getMiniRoomDto().getId()).get();
            bookingRepo.save(detailedBookingDtoToBooking(detailedBookingDto, customer, room));
            sendBookingConfirmationEmail(detailedBookingDto, customer, room);
            return "Booking added";
        }
        return "Booking not added";
    }

    private void sendBookingConfirmationEmail(DetailedBookingDto detailedBookingDto, Customer customer, Room room) {
        emailService.sendBookingConfirmedEmail(customer.getEMail(), "Booking Confirmation"
                , customer.getFirstName()
                , customer.getLastName()
                , detailedBookingDto.getBookingNumber()
                , detailedBookingDto.getStartDate().toString()
                , detailedBookingDto.getEndDate().toString()
                , detailedBookingDto.getTotalPrice());
    }

    @Override
    public String updateBooking(DetailedBookingDto detailedBookingDto) {
        if (checkIfBookingPossible(detailedBookingDto) && detailedBookingDto.getStartDate().isBefore(detailedBookingDto.getEndDate())) {
            Customer customer = bookingRepo.findById(detailedBookingDto.getId()).get().getCustomer();
            Room room = roomRepo.findById(detailedBookingDto.getMiniRoomDto().getId()).get();
            bookingRepo.save(detailedBookingDtoToBooking(detailedBookingDto, customer, room));
            return "Booking updated";
        }
        return "Booking not updated";
    }

    @Override
    public String deleteBooking(Long id) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if (booking == null) {
            return "Booking not found";
        }
        bookingRepo.deleteById(id);
        return "Booking deleted";
    }

    @Override
    public boolean checkIfBookingPossible(DetailedBookingDto booking) {
        List<Booking> overlappingBookings = bookingRepo.findAllByIdIsNotAndRoomIdAndStartDateIsBeforeAndEndDateIsAfter(booking.getId(), booking.getMiniRoomDto().getId(), booking.getEndDate(), booking.getStartDate());
        return overlappingBookings.isEmpty();
    }

    @Override
    public List<DetailedBookingDto> findBookingByDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(endDate)) {
            return bookingRepo.findAllByStartDateIsBeforeAndEndDateIsAfter(endDate, startDate)
                    .stream().map(this::bookingToDetailedBookingDto).toList();
        } else {
            return Collections.emptyList();
        }
    }
}
