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
public class MiniRoomDto {

    private Long id;
    private RoomType roomType;
    private int roomNumber;

}
