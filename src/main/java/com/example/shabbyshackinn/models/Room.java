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

    private RoomType roomType;
    private int roomNumber;

    private int beds;

    private int possibleExtraBeds;

    public Room(int roomNumber,RoomType roomType, int beds, int possibleExtraBeds) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.beds = beds;
        this.possibleExtraBeds = possibleExtraBeds;
    }
    
    
}
