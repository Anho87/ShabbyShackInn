package com.example.shabbyshackinn.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiniBookingDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private MiniRoomDto miniRoomDto;
    private MiniCustomerDto miniCustomerDto;
}
