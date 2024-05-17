package com.example.shabbyshackinn.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @NotNull
    //@Future
    private LocalDate endDate;

    @NotNull
    //@FutureOrPresent
    private LocalDate startDate;

    @Positive
    private int bookingNumber;

    @PositiveOrZero
    private int extraBedsWanted;

    @PositiveOrZero
    private int totalPrice;

    @Valid
    @ManyToOne
    @JoinColumn
    private Customer customer;
    
    @Valid
    @ManyToOne
    @JoinColumn
    private Room room;

    public Booking(Customer customer, LocalDate startDate, LocalDate endDate, int bookingNumber, int extraBedsWanted, int totalPrice, Room room) {
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingNumber = bookingNumber;
        this.extraBedsWanted = extraBedsWanted;
        this.room = room;
        this.totalPrice = totalPrice;
    }
}
