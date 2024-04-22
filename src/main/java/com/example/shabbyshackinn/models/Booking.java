package com.example.shabbyshackinn.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private int amountOfPersons;

    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    public Booking(Customer customer, int amountOfPersons, String startDate, String endDate) {
        this.customer = customer;
        this.amountOfPersons = amountOfPersons;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Room> room = new ArrayList<>();

    public void addRoom(Room r){
        room.add(r);
    }
}
