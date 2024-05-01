package com.example.shabbyshackinn.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private RoomType roomType;
    @Positive
    private int roomNumber;
    @Positive
    private int beds;
   
    private int possibleExtraBeds;

    public Room(int roomNumber,RoomType roomType, int beds, int possibleExtraBeds) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.beds = beds;
        this.possibleExtraBeds = possibleExtraBeds;
    }
    
    
}
