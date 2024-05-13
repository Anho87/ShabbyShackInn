package com.example.shabbyshackinn.dtos;

import com.example.shabbyshackinn.models.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedRoomDto {

    private Long id;
    private RoomType roomType;
    private int roomNumber;
    private int beds;
    private int price;
    private int possibleExtraBeds;

    public DetailedRoomDto(RoomType roomType, int roomNumber, int beds, int price, int possibleExtraBeds) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.beds = beds;
        this.price = price;
        this.possibleExtraBeds = possibleExtraBeds;
    }
}
