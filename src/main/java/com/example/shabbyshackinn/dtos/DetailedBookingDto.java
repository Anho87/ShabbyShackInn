package com.example.shabbyshackinn.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Random;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedBookingDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int bookingNumber;
    private int extraBedsWanted;
    private int totalPrice;
    private MiniCustomerDto miniCustomerDto;
    private MiniRoomDto miniRoomDto;

    public DetailedBookingDto(Long id, LocalDate startDate, LocalDate endDate, int bookingNumber, int extraBedsWanted, int totalPrice, MiniRoomDto miniRoomDto) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingNumber = bookingNumber;
        this.totalPrice = totalPrice;
        this.extraBedsWanted = extraBedsWanted;
        this.miniRoomDto = miniRoomDto;
    }

    public DetailedBookingDto(LocalDate startDate, LocalDate endDate, int bookingNumber, int extraBedsWanted, int totalPrice, MiniCustomerDto miniCustomerDto, MiniRoomDto miniRoomDto) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingNumber = bookingNumber;
        this.extraBedsWanted = extraBedsWanted;
        this.totalPrice = totalPrice;
        this.miniCustomerDto = miniCustomerDto;
        this.miniRoomDto = miniRoomDto;
    }
}
