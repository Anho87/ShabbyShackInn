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

    public DiscountServiceImpl(BookingRepo bookingRepo,RoomRepo roomRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
    }


    @Override
    public int calculateDiscount(Long roomId, Long customerId, DetailedBookingDto detailedBookingDto) {
        LocalDate start = detailedBookingDto.getStartDate();
        LocalDate end = detailedBookingDto.getEndDate();
        long daysBooked = ChronoUnit.DAYS.between(start, end);

        double totalDiscountedPrice = 0;
        double pricePerNight = roomRepo.findById(detailedBookingDto.getMiniRoomDto().getId()).get().getPrice();

        // 2% discount for nights between Sunday and Monday
        while (start.isBefore(end)) {
            if (start.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalDiscountedPrice += pricePerNight * 0.98;
            } else {
                totalDiscountedPrice += pricePerNight;
            }
            start = start.plusDays(1);
        }

        System.out.println(totalDiscountedPrice + "här");

        // 0.5% discount for booking longer than 1 night
        if (daysBooked >= 2) {
            totalDiscountedPrice *= 0.995;
        }
        System.out.println(totalDiscountedPrice);

        // 2% discount for customer with >= 10 nights booked last year
        LocalDate today = LocalDate.now();
        LocalDate lastYearStart = today.minusYears(1).withDayOfYear(1);
        LocalDate thisYearStart = today.withDayOfYear(1);
        Optional<Integer> nightsOptional = bookingRepo.sumNightsByCustomerIdAndYear(customerId, lastYearStart, thisYearStart);
        int nightsBookedLastYear = nightsOptional.orElse(0);
        if (nightsBookedLastYear >= 10) {
            totalDiscountedPrice *= 0.98;
        }
        System.out.println("nights booked last year" + nightsBookedLastYear);

        return (int) Math.round(totalDiscountedPrice);

    }

}
