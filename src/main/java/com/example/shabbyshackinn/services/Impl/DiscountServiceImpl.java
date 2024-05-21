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
        long daysBooked = ChronoUnit.DAYS.between(detailedBookingDto.getStartDate(), detailedBookingDto.getEndDate());
        double totalDiscount = 0;
        double pricePerNight = roomRepo.findById(roomId).get().getPrice();
        double totalStandardPrice = daysBooked * pricePerNight;

        totalDiscount += calculateSundayDiscount(detailedBookingDto, pricePerNight);
        totalDiscount += calculateLongStayDiscount(detailedBookingDto, totalStandardPrice);
        totalDiscount += calculateLoyaltyDiscount(customerId, totalStandardPrice);

        System.out.println("Total discount: " + totalDiscount);
        return (int) Math.round(totalStandardPrice - totalDiscount);
    }

    public double calculateLongStayDiscount(DetailedBookingDto detailedBookingDto, double totalStandardPrice) {
        long daysBooked = ChronoUnit.DAYS.between(detailedBookingDto.getStartDate(), detailedBookingDto.getEndDate());
        double longStayDiscount = 0;

        if (daysBooked >= 2) {
            longStayDiscount += totalStandardPrice * 0.005;
        }
        System.out.println("0.5% Discount after booking minimum 2 nights: " + longStayDiscount);
        return longStayDiscount;
    }

    public double calculateSundayDiscount(DetailedBookingDto detailedBookingDto, double pricePerNight) {
        LocalDate start = detailedBookingDto.getStartDate();
        LocalDate end = detailedBookingDto.getEndDate();
        double sundayDiscount = 0;

        while (start.isBefore(end)) {
            if (start.getDayOfWeek() == DayOfWeek.SUNDAY) {
                sundayDiscount += pricePerNight * 0.02;
            }
            start = start.plusDays(1);
        }
        System.out.println("2% Discount after Sunday deal: " + sundayDiscount);
        return sundayDiscount;
    }

    public double calculateLoyaltyDiscount(Long customerId, double totalStandardPrice) {
        LocalDate thisYearStart = LocalDate.now();
        LocalDate lastYearStart = thisYearStart.minusYears(1);
        Optional<Integer> nightsOptional = bookingRepo.sumNightsByCustomerIdAndYear(customerId, lastYearStart, thisYearStart);
        int nightsBookedLastYear = nightsOptional.orElse(0);
        double loyaltyDiscount = 0;

        if (nightsBookedLastYear >= 10) {
            loyaltyDiscount += totalStandardPrice * 0.02;
        }
        System.out.println("2% discount: " + loyaltyDiscount + " nights booked last year: " + nightsBookedLastYear);
        return loyaltyDiscount;
    }
}
