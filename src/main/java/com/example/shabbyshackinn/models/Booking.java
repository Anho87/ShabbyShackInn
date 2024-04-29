package com.example.shabbyshackinn.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue
    private Long id;
    
    @Future
    private LocalDate endDate;

    @NotNull
    private LocalDate startDate;

    
    private int bookingNumber;
    @Pattern(regexp = "[0-2]")
    private int extraBedsWanted;
    
    @Valid
    @ManyToOne
    @JoinColumn
    private Customer customer;
    
    @Valid
    @ManyToOne
    @JoinColumn
    private Room room;

    public Booking(Customer customer, LocalDate startDate, LocalDate endDate, int bookingNumber, int extraBedsWanted, Room room) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingNumber = bookingNumber;
        this.extraBedsWanted = extraBedsWanted;
        this.room = room;
    }
}
