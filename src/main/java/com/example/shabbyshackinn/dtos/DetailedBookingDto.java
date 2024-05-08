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
    private MiniCustomerDto miniCustomerDto;
    private MiniRoomDto miniRoomDto;

    public DetailedBookingDto(LocalDate startDate, LocalDate endDate,MiniCustomerDto miniCustomerDto,MiniRoomDto miniRoomDto) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.miniCustomerDto = miniCustomerDto;
        this.miniRoomDto = miniRoomDto;
    }

    public DetailedBookingDto(Long id, LocalDate startDate, LocalDate endDate, int extraBedsWanted, MiniRoomDto miniRoomDto) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraBedsWanted = extraBedsWanted;
        this.miniRoomDto = miniRoomDto;
    }

    public DetailedBookingDto (LocalDate startDate, LocalDate endDate, int bookingNumber, int extraBedsWanted, MiniCustomerDto miniCustomerDto, MiniRoomDto miniRoomDto) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingNumber = new Random().nextInt(9999);
        this.extraBedsWanted = extraBedsWanted;
        this.miniCustomerDto = miniCustomerDto;
        this.miniRoomDto = miniRoomDto;
    }
}
