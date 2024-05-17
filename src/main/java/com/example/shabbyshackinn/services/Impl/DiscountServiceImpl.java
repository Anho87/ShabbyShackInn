package com.example.shabbyshackinn.services.Impl;

import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.repos.BookingRepo;
import com.example.shabbyshackinn.repos.RoomRepo;
import com.example.shabbyshackinn.services.DiscountService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


@Service
public class DiscountServiceImpl implements DiscountService {

    final private BookingRepo bookingRepo;
    final private RoomRepo roomRepo;

    public DiscountServiceImpl(BookingRepo bookingRepo, RoomRepo roomRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }


    @Override
    public int calculateDiscount(Long roomId, Long customerId, DetailedBookingDto detailedBookingDto) {
        LocalDate start = detailedBookingDto.getStartDate();
        LocalDate end = detailedBookingDto.getEndDate();
        long daysBooked = ChronoUnit.DAYS.between(start, end);
        double totalDiscount = 0;
        double pricePerNight = roomRepo.findById(detailedBookingDto.getMiniRoomDto().getId()).get().getPrice();
        double totalStandardPrice = daysBooked * pricePerNight;

        // 2% discount for nights between Sunday and Monday pricePerNight * 0.005 - total
        while (start.isBefore(end)) {
            if (start.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalDiscount += pricePerNight * 0.02;
            }
            start = start.plusDays(1);
        }
        System.out.println("2% Discount after Sunday deal: "+totalDiscount);

        // 0.5% discount for booking longer than 1 night
        if (daysBooked >= 2) {
            totalDiscount += totalStandardPrice * 0.005;
        }
        System.out.println("0.5% Discount after booking minimum 2 nights: "+totalDiscount);

        // 2% discount for customer with >= 10 nights booked last year
        LocalDate today = LocalDate.now();
        LocalDate lastYearStart = today.minusYears(1);
        LocalDate thisYearStart = today;
        Optional<Integer> nightsOptional = bookingRepo.sumNightsByCustomerIdAndYear(customerId, lastYearStart, thisYearStart);
        int nightsBookedLastYear = nightsOptional.orElse(0);
        if (nightsBookedLastYear >= 10) {
            totalDiscount += totalStandardPrice * 0.02;
        }
        System.out.println("2% discount: " + totalDiscount + " nights booked last year: " + nightsBookedLastYear);

        return (int) Math.round(totalStandardPrice - totalDiscount);

    }

}
