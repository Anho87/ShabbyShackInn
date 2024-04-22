package com.example.shabbyshackinn.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Long id;

    private int roomNumber;

    private int beds;

    private int possibleExtraBed;

    public Room(int roomNumber, int beds, int possibleExtraBed) {
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.possibleExtraBed = possibleExtraBed;
    }
}
