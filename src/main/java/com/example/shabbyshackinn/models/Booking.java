package com.example.shabbyshackinn.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private int bookingNumber;
    private int extraBedsWanted;

    @ManyToOne
    @JoinColumn
    private Customer customer;
    
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
