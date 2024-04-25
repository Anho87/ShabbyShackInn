package com.example.shabbyshackinn.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedCustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String eMail;
    private List<MiniBookingDto> bookings;

    public DetailedCustomerDto(Long id, String firstName, String lastName, String phone, String eMail) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.eMail = eMail;
    }

    public DetailedCustomerDto(String firstName, String lastName, String phone, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.eMail = eMail;
    }
}
